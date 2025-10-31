package com.adv_java.inventory_management.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "stock_transactions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StockTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;

    @Column(length = 20, nullable = false)
    private String transactionType;

    @Column(nullable = false)
    private Integer quantity;

    @Column(length = 50)
    private String referenceNumber;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "performed_by")
    private User performedBy;

    @Column
    private LocalDateTime transactionDate;
}
