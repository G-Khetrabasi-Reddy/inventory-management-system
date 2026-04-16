package com.inventory.entity;

import com.inventory.enums.AuditStatus;
import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_audits",
        indexes = {
                @Index(name = "idx_audit_product", columnList = "product_id"),
                @Index(name = "idx_audit_warehouse", columnList = "warehouse_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @NotNull
    @Min(0)
    private Integer countedQuantity;

    @NotNull
    @Min(0)
    private Integer systemQuantity;

    private LocalDateTime auditDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audited_by")
    private User auditedBy;

    @Enumerated(EnumType.STRING)
    private AuditStatus status;

    @PrePersist
    protected void onCreate() {
        auditDate = LocalDateTime.now();
    }
}