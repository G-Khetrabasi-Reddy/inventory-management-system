package com.inventory.entity;

import com.inventory.enums.CapacityUnit;
import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "warehouses",
        indexes = {
                @Index(name = "idx_warehouse_name", columnList = "warehouseName")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseId;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String warehouseName;

    @Column(length = 255)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manager;

    @Column(precision = 12, scale = 2)
    private BigDecimal capacity;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private CapacityUnit capacityUnit;
}