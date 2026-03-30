package com.inventory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data // Generates equals() and hashCode() which are REQUIRED for IdClasses
@NoArgsConstructor // REQUIRED by JPA
@AllArgsConstructor
public class ProductSupplierId implements Serializable { // MUST implement Serializable

    private Long product;
    private Long supplier;

}