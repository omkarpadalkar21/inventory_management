package com.adv_java.inventory_management.repository;

import com.adv_java.inventory_management.domain.entities.PurchaseOrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItems, UUID> {
    List<PurchaseOrderItems> findByPurchaseOrderId(UUID purchaseOrderId);

    void deleteByPurchaseOrderId(UUID purchaseOrderId);
}

