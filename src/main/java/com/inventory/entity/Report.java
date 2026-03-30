package com.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @Column(nullable = false)
    private String reportName;

    private String reportType;   // SALES / PURCHASE / INVENTORY

    @ManyToOne
    @JoinColumn(name = "generated_by")
    private User generatedBy;

    private LocalDateTime generatedAt;

    @Column(columnDefinition = "TEXT")
    private String parameters;   // JSON string (filters, date range)

    private String storageType;  // FILE / DB

    private String storageUrl;   // file path or cloud URL
}