package com.inventory.entity;

import com.inventory.enums.MovementType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movementId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    // For transfer case
    @ManyToOne
    @JoinColumn(name = "destination_warehouse_id")
    private Warehouse destinationWarehouse;

    @Enumerated(EnumType.STRING)
    private MovementType movementType;

    private Integer quantity;

    // 🔥 Generic reference (VERY IMPORTANT DESIGN)
    private String referenceType;   // PO, SO, RETURN, DAMAGE, ADJUSTMENT
    private Long referenceId;

    @ManyToOne
    @JoinColumn(name = "performed_by")
    private User performedBy;

    private LocalDateTime movementDate;
}