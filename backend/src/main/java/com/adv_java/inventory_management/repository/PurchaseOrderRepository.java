package com.adv_java.inventory_management.repository;

import com.adv_java.inventory_management.domain.entities.PurchaseOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrders, UUID> {
    
    @Query("SELECT COUNT(po) > 0 FROM PurchaseOrders po WHERE po.supplier.id = :supplierId")
    boolean existsBySupplierId(@Param("supplierId") UUID supplierId);
    
    @Query("SELECT COUNT(po) FROM PurchaseOrders po WHERE po.supplier.id = :supplierId")
    long countBySupplierId(@Param("supplierId") UUID supplierId);
}

