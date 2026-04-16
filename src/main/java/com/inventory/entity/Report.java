package com.inventory.entity;

import com.inventory.enums.ReportType;
import com.inventory.enums.StorageType;
import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports",
        indexes = {
                @Index(name = "idx_report_type", columnList = "reportType"),
                @Index(name = "idx_report_user", columnList = "generated_by")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String reportName;

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generated_by")
    private User generatedBy;

    private LocalDateTime generatedAt;

    @Column(columnDefinition = "TEXT")
    private String parameters;

    @Enumerated(EnumType.STRING)
    private StorageType storageType;

    @Column(length = 500)
    private String storageUrl;

    @PrePersist
    protected void onCreate() {
        generatedAt = LocalDateTime.now();
    }
}