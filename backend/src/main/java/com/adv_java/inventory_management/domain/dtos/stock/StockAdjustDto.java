package com.adv_java.inventory_management.domain.dtos.stock;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockAdjustDto {
    @NotNull(message = "Product ID is required")
    private UUID productId;

    @NotNull(message = "Quantity is required")
    private Integer quantity; // Can be positive or negative

    private String referenceNumber;

    @NotBlank(message = "Notes are required for adjustments")
    private String notes;
}



