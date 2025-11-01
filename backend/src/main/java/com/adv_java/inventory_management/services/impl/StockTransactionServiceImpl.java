package com.adv_java.inventory_management.services.impl;

import com.adv_java.inventory_management.domain.dtos.stock.*;
import com.adv_java.inventory_management.domain.entities.*;
import com.adv_java.inventory_management.mapper.StockTransactionMapper;
import com.adv_java.inventory_management.repository.*;
import com.adv_java.inventory_management.services.StockTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockTransactionServiceImpl implements StockTransactionService {

    private final StockTransactionRepository stockTransactionRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final StockTransactionMapper stockTransactionMapper;

    @Override
    public List<StockTransactionDto> getAllTransactions() {
        return stockTransactionRepository.findAll().stream()
                .map(stockTransactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public StockTransactionDto getTransactionById(UUID id) {
        StockTransactions transaction = stockTransactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
        return stockTransactionMapper.toDto(transaction);
    }

    @Override
    public List<StockTransactionDto> getProductTransactionHistory(UUID productId) {
        return stockTransactionRepository.findByProductIdOrderByTransactionDateDesc(productId)
                .stream()
                .map(stockTransactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StockTransactionDto stockIn(StockInDto dto) {
        Products product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Update product quantity
        product.setQuantityInStock(product.getQuantityInStock() + dto.getQuantity());
        productRepository.save(product);

        // Create transaction record
        StockTransactions transaction = StockTransactions.builder()
                .product(product)
                .transactionType("STOCK_IN")
                .quantity(dto.getQuantity())
                .referenceNumber(dto.getReferenceNumber())
                .notes(dto.getNotes())
                .performedBy(getCurrentUser())
                .transactionDate(LocalDateTime.now())
                .build();

        StockTransactions savedTransaction = stockTransactionRepository.save(transaction);
        return stockTransactionMapper.toDto(savedTransaction);
    }

    @Override
    @Transactional
    public StockTransactionDto stockOut(StockOutDto dto) {
        Products product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if sufficient stock
        if (product.getQuantityInStock() < dto.getQuantity()) {
            throw new RuntimeException("Insufficient stock. Available: " + product.getQuantityInStock());
        }

        // Update product quantity
        product.setQuantityInStock(product.getQuantityInStock() - dto.getQuantity());
        productRepository.save(product);

        // Create transaction record
        StockTransactions transaction = StockTransactions.builder()
                .product(product)
                .transactionType("STOCK_OUT")
                .quantity(dto.getQuantity())
                .referenceNumber(dto.getReferenceNumber())
                .notes(dto.getNotes())
                .performedBy(getCurrentUser())
                .transactionDate(LocalDateTime.now())
                .build();

        StockTransactions savedTransaction = stockTransactionRepository.save(transaction);
        return stockTransactionMapper.toDto(savedTransaction);
    }

    @Override
    @Transactional
    public StockTransactionDto adjustStock(StockAdjustDto dto) {
        Products product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        int newQuantity = product.getQuantityInStock() + dto.getQuantity();
        if (newQuantity < 0) {
            throw new RuntimeException("Adjustment would result in negative stock");
        }

        // Update product quantity
        product.setQuantityInStock(newQuantity);
        productRepository.save(product);

        // Create transaction record
        StockTransactions transaction = StockTransactions.builder()
                .product(product)
                .transactionType("ADJUSTMENT")
                .quantity(dto.getQuantity())
                .referenceNumber(dto.getReferenceNumber())
                .notes(dto.getNotes())
                .performedBy(getCurrentUser())
                .transactionDate(LocalDateTime.now())
                .build();

        StockTransactions savedTransaction = stockTransactionRepository.save(transaction);
        return stockTransactionMapper.toDto(savedTransaction);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Current user not found"));
    }
}