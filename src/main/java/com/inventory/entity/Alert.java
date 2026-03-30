package com.inventory.entity;

import com.inventory.enums.AlertType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alertId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @Enumerated(EnumType.STRING)
    private AlertType alertType;

    private String message;

    private Integer thresholdValue;   // e.g., reorder level
    private Integer currentValue;     // current stock

    private String priority;          // LOW / MEDIUM / HIGH

    private Boolean isResolved = false;

    @ManyToOne
    @JoinColumn(name = "resolved_by")
    private User resolvedBy;

    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;
}
