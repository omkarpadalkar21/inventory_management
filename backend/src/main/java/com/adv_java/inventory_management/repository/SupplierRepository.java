package com.adv_java.inventory_management.repository;

import com.adv_java.inventory_management.domain.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
}

