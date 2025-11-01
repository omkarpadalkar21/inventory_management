package com.adv_java.inventory_management.domain.dtos.PurchaseOrder;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePurchaseOrderDto {
    @NotNull(message = "Supplier ID is required")
    private UUID supplierId;

    @NotNull(message = "Order date is required")
    private LocalDate orderDate;

    private LocalDate expectedDeliveryDate;

    @NotEmpty(message = "At least one item is required")
    private List<CreatePurchaseOrderItemDto> items;
}

