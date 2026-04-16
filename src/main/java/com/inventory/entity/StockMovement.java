package com.inventory.entity;

import com.inventory.enums.MovementType;
import com.inventory.enums.ReferenceType;
import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements",
        indexes = {
                @Index(name = "idx_sm_product", columnList = "product_id"),
                @Index(name = "idx_sm_warehouse", columnList = "warehouse_id"),
                @Index(name = "idx_sm_reference", columnList = "referenceType, referenceId")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockMovement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_warehouse_id")
    private Warehouse destinationWarehouse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType movementType;

    @NotNull
    @Min(1)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private ReferenceType referenceType;

    private Long referenceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performed_by")
    private User performedBy;

    @Column(nullable = false)
    private LocalDateTime movementDate;

    @PrePersist
    protected void onCreate() {
        movementDate = LocalDateTime.now();
    }
}