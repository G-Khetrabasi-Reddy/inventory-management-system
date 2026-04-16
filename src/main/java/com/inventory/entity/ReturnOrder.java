package com.inventory.entity;

import com.inventory.enums.ReturnStatus;
import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "return_orders",
        indexes = {
                @Index(name = "idx_return_so", columnList = "so_id"),
                @Index(name = "idx_return_product", columnList = "product_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long returnId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "so_id", nullable = false)
    private SalesOrder salesOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // From Module 3 (Warehouse)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    // Link with stock movement (IMPORTANT 🔥)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movement_id")
    private StockMovement stockMovement;

    @NotNull
    @Min(1)
    private Integer quantity;

    @Min(0)
    private Integer restockedQuantity = 0;

    private String reason;

    @Enumerated(EnumType.STRING)
    private ReturnStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "received_by")
    private User receivedBy;

    private LocalDate returnDate;

    @PrePersist
    @Override
    protected void onCreate() {
        super.onCreate();  // sets createdAt
        returnDate = LocalDate.now();
    }
}