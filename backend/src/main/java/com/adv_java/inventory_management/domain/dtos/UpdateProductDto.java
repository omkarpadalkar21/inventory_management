package com.adv_java.inventory_management.domain.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductDto {
    @Size(max = 50)
    private String sku;

    @Size(max = 200)
    private String name;

    private String description;
    private UUID categoryId;
    private UUID supplierId;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal unitPrice;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal costPrice;

    @Min(0)
    private Integer quantityInStock;

    @Min(0)
    private Integer reorderLevel;

    private String status;
}

