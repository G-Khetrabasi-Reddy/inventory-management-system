package com.inventory.mapper;

import com.inventory.dto.ReportDTO;
import com.inventory.entity.Report;

public class ReportMapper {

    public static ReportDTO toDTO(Report report) {
        if (report == null) return null;

        ReportDTO dto = new ReportDTO();

        dto.setReportId(report.getReportId());
        dto.setReportName(report.getReportName());
        dto.setReportType(report.getReportType());
        dto.setGeneratedAt(report.getGeneratedAt());
        dto.setParameters(report.getParameters());
        dto.setStorageType(report.getStorageType());
        dto.setStorageUrl(report.getStorageUrl());

        if (report.getGeneratedBy() != null)
            dto.setGeneratedBy(report.getGeneratedBy().getUserId());

        return dto;
    }
}