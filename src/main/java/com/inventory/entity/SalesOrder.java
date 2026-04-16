package com.inventory.entity;

import com.inventory.enums.SalesStatus;
import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "sales_orders",
        indexes = {
                @Index(name = "idx_so_customer", columnList = "customer_id"),
                @Index(name = "idx_so_status", columnList = "status")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrder extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long soId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @NotNull
    private LocalDate orderDate;

    private LocalDate deliveryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SalesStatus status = SalesStatus.PENDING;

    @NotNull
    @DecimalMin("0.0")
    @Column(precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(length = 500)
    private String notes;

    // Tracks which warehouse stock was reserved from (set during reserveStock)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @OneToMany(mappedBy = "salesOrder",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<SalesOrderItem> items;

    @PrePersist
    @Override
    protected void onCreate() {
        super.onCreate();  // sets createdAt
        orderDate = LocalDate.now();
    }
}