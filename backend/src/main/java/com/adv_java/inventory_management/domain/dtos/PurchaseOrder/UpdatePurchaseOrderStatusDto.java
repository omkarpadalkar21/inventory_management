package com.adv_java.inventory_management.domain.dtos.PurchaseOrder;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePurchaseOrderStatusDto {
    @NotBlank(message = "Status is required")
    private String status; // PENDING, APPROVED, RECEIVED, CANCELLED
}



