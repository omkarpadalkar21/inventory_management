// ProductRepository.java
package com.adv_java.inventory_management.repository;

import com.adv_java.inventory_management.domain.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Products, UUID> {
    @Query("SELECT p FROM Products p WHERE p.quantityInStock <= p.reorderLevel")
    List<Products> findLowStockProducts();

    @Query("SELECT p FROM Products p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.sku) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Products> searchProducts(@Param("keyword") String keyword);

    Page<Products> findAll(Pageable pageable);
    
    @Query("SELECT COUNT(p) > 0 FROM Products p WHERE p.supplier.id = :supplierId")
    boolean existsBySupplierId(@Param("supplierId") UUID supplierId);
    
    @Query("SELECT COUNT(p) FROM Products p WHERE p.supplier.id = :supplierId")
    long countBySupplierId(@Param("supplierId") UUID supplierId);
}
