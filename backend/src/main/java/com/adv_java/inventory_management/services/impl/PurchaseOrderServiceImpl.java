package com.adv_java.inventory_management.services.impl;

import com.adv_java.inventory_management.domain.dtos.PurchaseOrder.*;
import com.adv_java.inventory_management.domain.entities.*;
import com.adv_java.inventory_management.mapper.PurchaseOrderMapper;
import com.adv_java.inventory_management.repository.*;
import com.adv_java.inventory_management.services.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderItemRepository purchaseOrderItemRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PurchaseOrderMapper purchaseOrderMapper;

    @Override
    public List<PurchaseOrderDto> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseOrderDto getPurchaseOrderById(UUID id) {
        PurchaseOrders purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order not found with id: " + id));
        return mapToDto(purchaseOrder);
    }

    @Override
    @Transactional
    public PurchaseOrderDto createPurchaseOrder(CreatePurchaseOrderDto dto) {
        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        User currentUser = getCurrentUser();

        // Create purchase order
        PurchaseOrders purchaseOrder = PurchaseOrders.builder()
                .orderNumber(generateOrderNumber())
                .supplier(supplier)
                .orderDate(dto.getOrderDate())
                .expectedDeliveryDate(dto.getExpectedDeliveryDate())
                .status("PENDING")
                .createdBy(currentUser)
                .build();

        // Calculate total and create items
        BigDecimal totalAmount = BigDecimal.ZERO;
        PurchaseOrders savedOrder = purchaseOrderRepository.save(purchaseOrder);

        for (CreatePurchaseOrderItemDto itemDto : dto.getItems()) {
            Products product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            BigDecimal subtotal = itemDto.getUnitPrice()
                    .multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            totalAmount = totalAmount.add(subtotal);

            PurchaseOrderItems item = PurchaseOrderItems.builder()
                    .purchaseOrder(savedOrder)
                    .product(product)
                    .quantity(itemDto.getQuantity())
                    .unitPrice(itemDto.getUnitPrice())
                    .subtotal(subtotal)
                    .build();

            purchaseOrderItemRepository.save(item);
        }

        savedOrder.setTotalAmount(totalAmount);
        purchaseOrderRepository.save(savedOrder);

        return mapToDto(savedOrder);
    }

    @Override
    @Transactional
    public PurchaseOrderDto updatePurchaseOrder(UUID id, UpdatePurchaseOrderDto dto) {
        PurchaseOrders purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order not found"));

        if (dto.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
            purchaseOrder.setSupplier(supplier);
        }

        if (dto.getOrderDate() != null) {
            purchaseOrder.setOrderDate(dto.getOrderDate());
        }

        if (dto.getExpectedDeliveryDate() != null) {
            purchaseOrder.setExpectedDeliveryDate(dto.getExpectedDeliveryDate());
        }

        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            // Delete existing items
            purchaseOrderItemRepository.deleteByPurchaseOrderId(id);

            // Create new items
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (CreatePurchaseOrderItemDto itemDto : dto.getItems()) {
                Products product = productRepository.findById(itemDto.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                BigDecimal subtotal = itemDto.getUnitPrice()
                        .multiply(BigDecimal.valueOf(itemDto.getQuantity()));
                totalAmount = totalAmount.add(subtotal);

                PurchaseOrderItems item = PurchaseOrderItems.builder()
                        .purchaseOrder(purchaseOrder)
                        .product(product)
                        .quantity(itemDto.getQuantity())
                        .unitPrice(itemDto.getUnitPrice())
                        .subtotal(subtotal)
                        .build();

                purchaseOrderItemRepository.save(item);
            }
            purchaseOrder.setTotalAmount(totalAmount);
        }

        PurchaseOrders updatedOrder = purchaseOrderRepository.save(purchaseOrder);
        return mapToDto(updatedOrder);
    }

    @Override
    @Transactional
    public PurchaseOrderDto updatePurchaseOrderStatus(UUID id, UpdatePurchaseOrderStatusDto dto) {
        PurchaseOrders purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order not found"));

        purchaseOrder.setStatus(dto.getStatus());

        // If status is RECEIVED, update product stock
        if ("RECEIVED".equals(dto.getStatus())) {
            List<PurchaseOrderItems> items = purchaseOrderItemRepository.findByPurchaseOrderId(id);
            for (PurchaseOrderItems item : items) {
                Products product = item.getProduct();
                product.setQuantityInStock(product.getQuantityInStock() + item.getQuantity());
                productRepository.save(product);
            }
        }

        PurchaseOrders updatedOrder = purchaseOrderRepository.save(purchaseOrder);
        return mapToDto(updatedOrder);
    }

    @Override
    @Transactional
    public void deletePurchaseOrder(UUID id) {
        if (!purchaseOrderRepository.existsById(id)) {
            throw new RuntimeException("Purchase order not found");
        }
        purchaseOrderItemRepository.deleteByPurchaseOrderId(id);
        purchaseOrderRepository.deleteById(id);
    }

    private PurchaseOrderDto mapToDto(PurchaseOrders purchaseOrder) {
        PurchaseOrderDto dto = purchaseOrderMapper.toDto(purchaseOrder);

        // Fetch and map items
        List<PurchaseOrderItems> items = purchaseOrderItemRepository
                .findByPurchaseOrderId(purchaseOrder.getId());
        List<PurchaseOrderItemDto> itemDtos = items.stream()
                .map(purchaseOrderMapper::toItemDto)
                .collect(Collectors.toList());

        dto.setItems(itemDtos);
        return dto;
    }

    private String generateOrderNumber() {
        return "PO-" + System.currentTimeMillis();
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Current user not found"));
    }
}