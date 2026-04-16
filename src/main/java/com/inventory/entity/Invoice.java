package com.inventory.entity;

import com.inventory.enums.InvoiceStatus;
import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "invoices",
        indexes = {
                @Index(name = "idx_invoice_customer", columnList = "customer_id"),
                @Index(name = "idx_invoice_sales_order", columnList = "so_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "so_id", nullable = false)
    private SalesOrder salesOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private LocalDate generatedDate;
    private LocalDate dueDate;

    @NotNull
    @DecimalMin("0.0")
    @Column(precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    @PrePersist
    protected void onCreate() {
        generatedDate = LocalDate.now();
    }
}