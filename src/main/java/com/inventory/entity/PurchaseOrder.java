package com.inventory.entity;

import com.inventory.enums.PurchaseStatus;
import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "purchase_orders",
        indexes = {
                @Index(name = "idx_po_supplier", columnList = "supplier_id"),
                @Index(name = "idx_po_status", columnList = "status")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @NotNull
    private LocalDate orderDate;

    private LocalDate expectedDate;
    private LocalDate receivedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PurchaseStatus status = PurchaseStatus.PENDING;

    @NotNull
    @DecimalMin("0.0")
    @Column(precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(length = 500)
    private String notes;

    @OneToMany(mappedBy = "purchaseOrder",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<PurchaseOrderItem> items;

    @PrePersist
    @Override
    protected void onCreate() {
        super.onCreate();  // sets createdAt
        orderDate = LocalDate.now();
    }
}