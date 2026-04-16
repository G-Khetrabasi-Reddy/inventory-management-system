package com.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "purchase_order_items",
        indexes = {
                @Index(name = "idx_poi_po", columnList = "po_id"),
                @Index(name = "idx_poi_product", columnList = "product_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"po_id", "product_id"})
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "po_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Add this field to the entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @NotNull
    @Min(1)
    private Integer quantityOrdered;

    @Min(0)
    private Integer quantityReceived = 0;

    @NotNull
    @DecimalMin("0.0")
    @Column(precision = 12, scale = 2)
    private BigDecimal unitCost;

    @DecimalMin("0.0")
    @Column(precision = 12, scale = 2)
    private BigDecimal totalCost;

    // A public domain method to set up the item state
    public void initializePricing(Integer quantityOrdered, BigDecimal unitCost) {
        this.quantityOrdered = quantityOrdered;
        this.unitCost = unitCost;
        this.calculateTotal(); // Internal state managed internally
    }

    @PrePersist
    @PreUpdate
    protected void calculateTotal() {
        if (unitCost != null && quantityOrdered != null) {
            totalCost = unitCost.multiply(BigDecimal.valueOf(quantityOrdered));
        }
    }
}