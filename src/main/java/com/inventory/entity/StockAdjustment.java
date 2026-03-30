package com.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_adjustments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockAdjustment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adjustmentId;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Warehouse warehouse;

    @ManyToOne
    private InventoryAudit audit;

    private Integer adjustmentQuantity;

    private String reason;

    @ManyToOne
    private User adjustedBy;

    private LocalDateTime adjustedAt;
}
