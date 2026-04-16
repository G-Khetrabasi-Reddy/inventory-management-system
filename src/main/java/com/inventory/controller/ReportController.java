package com.inventory.controller;

import com.inventory.dto.ReportDTO;
import com.inventory.enums.ReportType;
import com.inventory.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // 🔥 GENERATE REPORT
    @PostMapping
    public ReportDTO generateReport(
            @RequestParam ReportType type,
            @RequestParam Long userId
    ) {

        return switch (type) {
            case SALES -> reportService.generateSalesReport(userId);
            case INVENTORY -> reportService.generateInventoryReport(userId);
            case PURCHASE -> reportService.generatePurchaseReport(userId);
            default -> throw new RuntimeException("Invalid report type");
        };
    }

    // 🔥 GET REPORT BY ID
    @GetMapping("/{id}")
    public ReportDTO getReportById(@PathVariable Long id) {
        return reportService.getReportById(id);
    }
}