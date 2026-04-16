package com.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "damage_records",
        indexes = {
                @Index(name = "idx_damage_product", columnList = "product_id"),
                @Index(name = "idx_damage_warehouse", columnList = "warehouse_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DamageRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long damageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movement_id")
    private StockMovement movement;

    @NotNull
    @Min(1)
    private Integer quantity;

    @Column(length = 255)
    private String reason;

    @Column(nullable = false)
    private LocalDateTime reportedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_by")
    private User reportedBy;

    @PrePersist
    protected void onCreate() {
        reportedAt = LocalDateTime.now();
    }
}