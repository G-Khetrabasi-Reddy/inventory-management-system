package com.inventory.entity;

import com.inventory.enums.SalesStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "sales_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long soId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    private LocalDate orderDate;
    private LocalDate deliveryDate;

    @Enumerated(EnumType.STRING)
    private SalesStatus status;

    private BigDecimal totalAmount;

    private String notes;
}
