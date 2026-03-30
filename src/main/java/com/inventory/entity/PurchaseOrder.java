package com.inventory.entity;

import com.inventory.enums.PurchaseStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "purchase_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poId;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    private LocalDate orderDate;
    private LocalDate expectedDate;
    private LocalDate receivedDate;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;

    private BigDecimal totalAmount;

    private String notes;
}
