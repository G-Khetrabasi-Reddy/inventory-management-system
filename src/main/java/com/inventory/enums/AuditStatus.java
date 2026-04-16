package com.inventory.enums;

public enum AuditStatus {

    PENDING,      // Audit created but not completed
    COMPLETED,    // Audit done, no discrepancy
    ADJUSTED,      // Discrepancy found and stock adjusted
    MATCHED,
    MISMATCH
}
