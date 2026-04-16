package com.inventory.service.impl;

import com.inventory.dto.ReportDTO;
import com.inventory.entity.Report;
import com.inventory.entity.User;
import com.inventory.enums.ReportType;
import com.inventory.enums.StorageType;
import com.inventory.mapper.ReportMapper;
import com.inventory.repository.ReportRepository;
import com.inventory.repository.UserRepository;
import com.inventory.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    // 🔥 SALES REPORT
    @Override
    public ReportDTO generateSalesReport(Long userId) {
        return generateReport("Sales Report", ReportType.SALES, userId);
    }

    // 🔥 INVENTORY REPORT
    @Override
    public ReportDTO generateInventoryReport(Long userId) {
        return generateReport("Inventory Report", ReportType.INVENTORY, userId);
    }

    // 🔥 PURCHASE REPORT
    @Override
    public ReportDTO generatePurchaseReport(Long userId) {
        return generateReport("Purchase Report", ReportType.PURCHASE, userId);
    }

    // 🔥 COMMON METHOD (BEST DESIGN)
    private ReportDTO generateReport(String name, ReportType type, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Report report = new Report();
        report.setReportName(name);
        report.setReportType(type);
        report.setGeneratedBy(user);

        // 🔥 dummy data (later replace with aggregation logic)
        report.setParameters("{\"generated\":\"true\"}");
        report.setStorageType(StorageType.DATABASE);
        report.setStorageUrl("INTERNAL_DB");

        return ReportMapper.toDTO(reportRepository.save(report));
    }

    // 🔥 FETCH REPORT
    @Override
    public ReportDTO getReportById(Long reportId) {

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        return ReportMapper.toDTO(report);
    }
}