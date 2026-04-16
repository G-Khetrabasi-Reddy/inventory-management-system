package com.inventory.entity;

import com.inventory.enums.PaymentMethod;
import com.inventory.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "supplier_payments",
        indexes = {
                @Index(name = "idx_sp_po", columnList = "po_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierPayment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierPaymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "po_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paid_by", nullable = false)
    private User paidBy;

    @NotNull
    @DecimalMin("0.0")
    @Column(precision = 12, scale = 2)
    private BigDecimal amountPaid;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDate paymentDate;

    @PrePersist
    protected void onCreate() {
        paymentDate = LocalDate.now();
    }
}