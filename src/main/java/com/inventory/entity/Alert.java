package com.inventory.entity;

import com.inventory.enums.AlertType;
import com.inventory.enums.Priority;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alert extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alertId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @Enumerated(EnumType.STRING)
    private AlertType alertType;

    private String message;

    private Integer thresholdValue;   // e.g., reorder level
    private Integer currentValue;     // current stock

    @Enumerated(EnumType.STRING)
    private Priority priority;          // LOW / MEDIUM / HIGH

    private Boolean isResolved = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resolved_by")
    private User resolvedBy;

    private LocalDateTime resolvedAt;
}
