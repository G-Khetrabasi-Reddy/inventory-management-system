package com.inventory.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductSupplierId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long productId;
    private Long supplierId;
}