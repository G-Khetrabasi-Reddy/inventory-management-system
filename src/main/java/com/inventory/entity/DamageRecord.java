package com.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "damage_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DamageRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long damageId;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Warehouse warehouse;

    @ManyToOne
    private StockMovement movement;

    private Integer quantity;
    private String reason;

    private LocalDateTime reportedAt;

    @ManyToOne
    private User reportedBy;
}
