package com.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "sales_order_items",
        indexes = {
                @Index(name = "idx_soi_so", columnList = "so_id"),
                @Index(name = "idx_soi_product", columnList = "product_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"so_id", "product_id"})
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long soItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "so_id", nullable = false)
    private SalesOrder salesOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    @Min(1)
    private Integer quantityOrdered;

    @Min(0)
    private Integer quantityDelivered = 0;

    @NotNull
    @DecimalMin("0.0")
    @Column(precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @DecimalMin("0.0")
    @Column(precision = 12, scale = 2)
    private BigDecimal totalPrice;

    @PrePersist
    @PreUpdate
    protected void calculateTotal() {
        if (unitPrice != null && quantityOrdered != null) {
            totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantityOrdered));
        }
    }
}