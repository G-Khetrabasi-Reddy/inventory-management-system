package com.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_supplier")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ProductSupplierId.class)
public class ProductSupplier { //PRODUCT_SUPPLIER (JOIN ENTITY)

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Id
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    private String supplierProductCode;

    private Double unitCost;

    private Integer leadTimeDays;

    private Boolean isPreferred;

    private Boolean isActive = true;
}