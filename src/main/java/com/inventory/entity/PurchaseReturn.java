package com.inventory.entity;

import com.inventory.enums.ReturnStatus;
import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "purchase_returns",
        indexes = {
                @Index(name = "idx_pr_po", columnList = "po_id"),
                @Index(name = "idx_pr_product", columnList = "product_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseReturn implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poReturnId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "po_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movement_id")
    private StockMovement movement;

    @NotNull
    @Min(1)
    private Integer quantity;

    @Column(length = 255)
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "returned_by")
    private User returnedBy;

    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    private ReturnStatus status;

    @PrePersist
    protected void onCreate() {
        returnDate = LocalDate.now();
    }
}