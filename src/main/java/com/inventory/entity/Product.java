package com.inventory.entity;

import com.inventory.enums.UnitOfMeasure;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "products",
        indexes = {
                @Index(name = "idx_product_sku", columnList = "sku")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod_gen")
    @SequenceGenerator(name = "prod_gen", sequenceName = "product_seq", allocationSize = 1)
    private Long productId;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String productName;

    @NotBlank
    @Column(nullable = false, unique = true, length = 100)
    private String sku;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @DecimalMin("0.0")
    @Column(precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @DecimalMin("0.0")
    @Column(precision = 12, scale = 2)
    private BigDecimal costPrice;

    @Min(0)
    private Integer reorderLevel = 0;
    @Min(0)
    private Integer reorderQuantity = 0;

    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UnitOfMeasure unitOfMeasure;

    @Column(nullable = false)
    private Boolean isActive = true;
}