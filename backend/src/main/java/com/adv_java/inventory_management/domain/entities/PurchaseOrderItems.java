package com.adv_java.inventory_management.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "purchase_order_items")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PurchaseOrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
private UUID id;

    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrders purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal;
}
