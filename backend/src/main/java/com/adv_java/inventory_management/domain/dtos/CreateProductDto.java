package com.adv_java.inventory_management.domain.dtos;

import jakarta.validation.constraints .*;
import lombok .*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductDto {
    @NotBlank(message = "SKU is required")
    @Size(max = 50)
    private String sku;

    @NotBlank(message = "Name is required")
    @Size(max = 200)
    private String name;

    private String description;

    @NotNull(message = "Category is required")
    private UUID categoryId;

    private UUID supplierId;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal unitPrice;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal costPrice;

    @Min(0)
    private Integer quantityInStock = 0;

    @Min(0)
    private Integer reorderLevel = 10;

    private String status = "ACTIVE";
}

