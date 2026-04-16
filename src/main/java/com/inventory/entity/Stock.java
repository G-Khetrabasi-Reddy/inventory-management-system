package com.inventory.entity;

import com.inventory.enums.StockStatus;
import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.Min;

import java.io.Serializable;

@Entity
@Table(name = "stock",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"product_id", "warehouse_id"})
        },
        indexes = {
                @Index(name = "idx_stock_product", columnList = "product_id"),
                @Index(name = "idx_stock_warehouse", columnList = "warehouse_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stock extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Min(0)
    @Column(nullable = false)
    private Integer quantityAvailable = 0;

    @Min(0)
    @Column(nullable = false)
    private Integer quantityReserved = 0;

    @Min(0)
    @Column(nullable = false)
    private Integer quantityOnOrder = 0;

    @Min(0)
    private Integer reorderLevelOverride;

    @Min(0)
    private Integer reorderQtyOverride;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StockStatus stockStatus = StockStatus.ACTIVE;

    @Version
    private Long version;
}