package com.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_adjustments",
        indexes = {
                @Index(name = "idx_adj_product", columnList = "product_id"),
                @Index(name = "idx_adj_warehouse", columnList = "warehouse_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockAdjustment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adjustmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audit_id")
    private InventoryAudit audit;

    @NotNull
    private Integer adjustmentQuantity; // can be + or -

    @Column(length = 255)
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adjusted_by")
    private User adjustedBy;

    @Column(nullable = false)
    private LocalDateTime adjustedAt;

    @PrePersist
    protected void onCreate() {
        adjustedAt = LocalDateTime.now();
    }
}