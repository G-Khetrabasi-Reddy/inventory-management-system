package com.inventory.entity;

import com.inventory.enums.PaymentMethod;
import com.inventory.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments",
        indexes = {
                @Index(name = "idx_payment_invoice", columnList = "invoice_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "so_id", nullable = false)
    private SalesOrder salesOrder;

    @NotNull
    @DecimalMin("0.0")
    @Column(precision = 12, scale = 2)
    private BigDecimal amountPaid;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @PrePersist
    protected void onCreate() {
        paymentDate = LocalDate.now();
    }
}