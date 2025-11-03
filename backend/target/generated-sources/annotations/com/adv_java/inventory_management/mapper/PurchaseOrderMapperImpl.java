package com.adv_java.inventory_management.mapper;

import com.adv_java.inventory_management.domain.dtos.PurchaseOrder.PurchaseOrderDto;
import com.adv_java.inventory_management.domain.dtos.PurchaseOrder.PurchaseOrderItemDto;
import com.adv_java.inventory_management.domain.entities.Products;
import com.adv_java.inventory_management.domain.entities.PurchaseOrderItems;
import com.adv_java.inventory_management.domain.entities.PurchaseOrders;
import com.adv_java.inventory_management.domain.entities.Supplier;
import com.adv_java.inventory_management.domain.entities.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-03T11:29:49+0530",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class PurchaseOrderMapperImpl implements PurchaseOrderMapper {

    @Override
    public PurchaseOrderDto toDto(PurchaseOrders purchaseOrder) {
        if ( purchaseOrder == null ) {
            return null;
        }

        PurchaseOrderDto.PurchaseOrderDtoBuilder purchaseOrderDto = PurchaseOrderDto.builder();

        purchaseOrderDto.supplierId( purchaseOrderSupplierId( purchaseOrder ) );
        purchaseOrderDto.supplierName( purchaseOrderSupplierName( purchaseOrder ) );
        purchaseOrderDto.createdById( purchaseOrderCreatedById( purchaseOrder ) );
        purchaseOrderDto.createdByUsername( purchaseOrderCreatedByUsername( purchaseOrder ) );
        purchaseOrderDto.createdAt( purchaseOrder.getCreatedAt() );
        purchaseOrderDto.expectedDeliveryDate( purchaseOrder.getExpectedDeliveryDate() );
        purchaseOrderDto.id( purchaseOrder.getId() );
        purchaseOrderDto.orderDate( purchaseOrder.getOrderDate() );
        purchaseOrderDto.orderNumber( purchaseOrder.getOrderNumber() );
        purchaseOrderDto.status( purchaseOrder.getStatus() );
        purchaseOrderDto.totalAmount( purchaseOrder.getTotalAmount() );
        purchaseOrderDto.updatedAt( purchaseOrder.getUpdatedAt() );

        return purchaseOrderDto.build();
    }

    @Override
    public PurchaseOrderItemDto toItemDto(PurchaseOrderItems item) {
        if ( item == null ) {
            return null;
        }

        PurchaseOrderItemDto.PurchaseOrderItemDtoBuilder purchaseOrderItemDto = PurchaseOrderItemDto.builder();

        purchaseOrderItemDto.productId( itemProductId( item ) );
        purchaseOrderItemDto.productName( itemProductName( item ) );
        purchaseOrderItemDto.id( item.getId() );
        purchaseOrderItemDto.quantity( item.getQuantity() );
        purchaseOrderItemDto.subtotal( item.getSubtotal() );
        purchaseOrderItemDto.unitPrice( item.getUnitPrice() );

        return purchaseOrderItemDto.build();
    }

    private UUID purchaseOrderSupplierId(PurchaseOrders purchaseOrders) {
        Supplier supplier = purchaseOrders.getSupplier();
        if ( supplier == null ) {
            return null;
        }
        return supplier.getId();
    }

    private String purchaseOrderSupplierName(PurchaseOrders purchaseOrders) {
        Supplier supplier = purchaseOrders.getSupplier();
        if ( supplier == null ) {
            return null;
        }
        return supplier.getName();
    }

    private UUID purchaseOrderCreatedById(PurchaseOrders purchaseOrders) {
        User createdBy = purchaseOrders.getCreatedBy();
        if ( createdBy == null ) {
            return null;
        }
        return createdBy.getId();
    }

    private String purchaseOrderCreatedByUsername(PurchaseOrders purchaseOrders) {
        User createdBy = purchaseOrders.getCreatedBy();
        if ( createdBy == null ) {
            return null;
        }
        return createdBy.getUsername();
    }

    private UUID itemProductId(PurchaseOrderItems purchaseOrderItems) {
        Products product = purchaseOrderItems.getProduct();
        if ( product == null ) {
            return null;
        }
        return product.getId();
    }

    private String itemProductName(PurchaseOrderItems purchaseOrderItems) {
        Products product = purchaseOrderItems.getProduct();
        if ( product == null ) {
            return null;
        }
        return product.getName();
    }
}
