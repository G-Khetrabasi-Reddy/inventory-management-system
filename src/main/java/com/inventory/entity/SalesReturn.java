package com.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "sales_returns")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long returnId;

    @ManyToOne
    @JoinColumn(name = "so_id", nullable = false)
    private SalesOrder salesOrder;

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
    private Integer restockedQuantity;

    private String reason;
    private String status;

    @ManyToOne
    @JoinColumn(name = "received_by")
    private User receivedBy;

    private LocalDate returnDate;
}
