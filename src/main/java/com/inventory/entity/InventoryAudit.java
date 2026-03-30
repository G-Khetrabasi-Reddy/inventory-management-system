package com.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_audits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Warehouse warehouse;

    private Integer countedQuantity;
    private Integer systemQuantity;

    private LocalDateTime auditDate;

    @ManyToOne
    private User auditedBy;

    private String status;
}