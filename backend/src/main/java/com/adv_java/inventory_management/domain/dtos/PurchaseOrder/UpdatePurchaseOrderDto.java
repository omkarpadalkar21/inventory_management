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
public class UpdatePurchaseOrderDto {
    private UUID supplierId;
    private LocalDate orderDate;
    private LocalDate expectedDeliveryDate;
    private List<CreatePurchaseOrderItemDto> items;
}

