package com.adv_java.inventory_management.domain.dtos.stock;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockTransactionDto {
    private UUID id;
    private UUID productId;
    private String productName;
    private String transactionType;
    private Integer quantity;
    private String referenceNumber;
    private String notes;
    private UUID performedById;
    private String performedByUsername;
    private LocalDateTime transactionDate;
}

// StockInDto.java
