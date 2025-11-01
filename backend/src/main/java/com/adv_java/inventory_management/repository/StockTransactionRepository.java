package com.adv_java.inventory_management.repository;

import com.adv_java.inventory_management.domain.entities.StockTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StockTransactionRepository extends JpaRepository<StockTransactions, UUID> {
    List<StockTransactions> findByProductIdOrderByTransactionDateDesc(UUID productId);
}
