package com.inventory.repository;

import com.inventory.entity.Report;
import com.inventory.enums.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    // 🔥 Filter reports by type
    List<Report> findByReportType(ReportType reportType);

    // 🔥 User-specific reports
    List<Report> findByGeneratedBy_UserId(Long userId);
}