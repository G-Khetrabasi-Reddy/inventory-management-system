package com.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "product_supplier",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"product_id", "supplier_id"})
        },
        indexes = {
                @Index(name = "idx_ps_product", columnList = "product_id"),
                @Index(name = "idx_ps_supplier", columnList = "supplier_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ProductSupplierId.class)
public class ProductSupplier {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(length = 100)
    private String supplierProductCode;

    @DecimalMin("0.0")
    @Column(precision = 12, scale = 2)
    private BigDecimal unitCost;

    @NotNull
    @Min(0)
    private Integer leadTimeDays;

    @Column(nullable = false)
    private Boolean isPreferred = false;

    @Column(nullable = false)
    private Boolean isActive = true;
}