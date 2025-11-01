package com.adv_java.inventory_management.controllers;

import com.adv_java.inventory_management.domain.dtos.stock.*;
import com.adv_java.inventory_management.services.StockTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stock-transactions")
@RequiredArgsConstructor
public class StockTransactionController {

    private final StockTransactionService stockTransactionService;

    @GetMapping
    public ResponseEntity<List<StockTransactionDto>> getAllTransactions() {
        return ResponseEntity.ok(stockTransactionService.getAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockTransactionDto> getTransactionById(@PathVariable UUID id) {
        return ResponseEntity.ok(stockTransactionService.getTransactionById(id));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<StockTransactionDto>> getProductTransactionHistory(
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(stockTransactionService.getProductTransactionHistory(productId));
    }

    @PostMapping("/stock-in")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<StockTransactionDto> stockIn(@Valid @RequestBody StockInDto dto) {
        return new ResponseEntity<>(stockTransactionService.stockIn(dto), HttpStatus.CREATED);
    }

    @PostMapping("/stock-out")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    public ResponseEntity<StockTransactionDto> stockOut(@Valid @RequestBody StockOutDto dto) {
        return new ResponseEntity<>(stockTransactionService.stockOut(dto), HttpStatus.CREATED);
    }

    @PostMapping("/adjust")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<StockTransactionDto> adjustStock(@Valid @RequestBody StockAdjustDto dto) {
        return new ResponseEntity<>(stockTransactionService.adjustStock(dto), HttpStatus.CREATED);
    }
}