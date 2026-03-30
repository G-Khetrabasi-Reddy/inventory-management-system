package com.inventory.entity;

import com.inventory.enums.StockStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "stock",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"product_id", "warehouse_id"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    private Integer quantityAvailable;
    private Integer quantityReserved;
    private Integer quantityOnOrder;

    private Integer reorderLevelOverride;
    private Integer reorderQtyOverride;

    @Enumerated(EnumType.STRING)
    private StockStatus stockStatus;

    private LocalDateTime lastUpdated;
}
