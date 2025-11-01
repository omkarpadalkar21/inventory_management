package com.adv_java.inventory_management.mapper;

import com.adv_java.inventory_management.domain.dtos.PurchaseOrder.PurchaseOrderDto;
import com.adv_java.inventory_management.domain.dtos.PurchaseOrder.PurchaseOrderItemDto;
import com.adv_java.inventory_management.domain.entities.PurchaseOrderItems;
import com.adv_java.inventory_management.domain.entities.PurchaseOrders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PurchaseOrderMapper {

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.name", target = "supplierName")
    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy.username", target = "createdByUsername")
    @Mapping(target = "items", ignore = true)
    PurchaseOrderDto toDto(PurchaseOrders purchaseOrder);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    PurchaseOrderItemDto toItemDto(PurchaseOrderItems item);
}
