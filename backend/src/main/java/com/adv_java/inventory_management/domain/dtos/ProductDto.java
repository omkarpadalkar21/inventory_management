package com.adv_java.inventory_management.domain.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private UUID id;
    private String sku;
    private String name;
    private String description;
    private UUID categoryId;
    private String categoryName;
    private UUID supplierId;
    private String supplierName;
    private BigDecimal unitPrice;
    private BigDecimal costPrice;
    private Integer quantityInStock;
    private Integer reorderLevel;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

