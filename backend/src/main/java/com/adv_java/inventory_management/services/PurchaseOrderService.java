package com.adv_java.inventory_management.services;

import com.adv_java.inventory_management.domain.dtos.PurchaseOrder.*;
import java.util.List;
import java.util.UUID;

public interface PurchaseOrderService {
    List<PurchaseOrderDto> getAllPurchaseOrders();
    PurchaseOrderDto getPurchaseOrderById(UUID id);
    PurchaseOrderDto createPurchaseOrder(CreatePurchaseOrderDto dto);
    PurchaseOrderDto updatePurchaseOrder(UUID id, UpdatePurchaseOrderDto dto);
    PurchaseOrderDto updatePurchaseOrderStatus(UUID id, UpdatePurchaseOrderStatusDto dto);
    void deletePurchaseOrder(UUID id);
}