package com.adv_java.inventory_management.domain.dtos.stock;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockInDto {
    @NotNull(message = "Product ID is required")
    private UUID productId;

    @NotNull(message = "Quantity is required")
    @Min(1)
    private Integer quantity;

    private String referenceNumber;
    private String notes;
}

