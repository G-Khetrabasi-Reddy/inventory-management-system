package com.inventory.service;

import com.inventory.dto.ReportDTO;

public interface ReportService {

    ReportDTO generateSalesReport(Long userId);

    ReportDTO generateInventoryReport(Long userId);

    ReportDTO generatePurchaseReport(Long userId);

    ReportDTO getReportById(Long reportId);
}