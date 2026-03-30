package com.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "purchase_returns")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poReturnId;

    @ManyToOne
    @JoinColumn(name = "po_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @OneToOne
    @JoinColumn(name = "movement_id")
    private StockMovement movement;

    private Integer quantity;

    private String reason;

    @ManyToOne
    @JoinColumn(name = "returned_by")
    private User returnedBy;

    private LocalDate returnDate;

    private String status;
}