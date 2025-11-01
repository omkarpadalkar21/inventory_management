package com.adv_java.inventory_management.services;

import com.adv_java.inventory_management.domain.dtos.stock.*;
import java.util.List;
import java.util.UUID;

public interface StockTransactionService {
    List<StockTransactionDto> getAllTransactions();
    StockTransactionDto getTransactionById(UUID id);
    List<StockTransactionDto> getProductTransactionHistory(UUID productId);
    StockTransactionDto stockIn(StockInDto dto);
    StockTransactionDto stockOut(StockOutDto dto);
    StockTransactionDto adjustStock(StockAdjustDto dto);
}