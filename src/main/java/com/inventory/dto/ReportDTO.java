package com.inventory.dto;

import com.inventory.enums.ReportType;
import com.inventory.enums.StorageType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {

    private Long reportId;

    private String reportName;
    private ReportType reportType;

    private Long generatedBy;
    private LocalDateTime generatedAt;

    private String parameters;

    private StorageType storageType;
    private String storageUrl;
}