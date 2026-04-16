package com.inventory.enums;

public enum InvoiceStatus {

    GENERATED,        // Invoice created but not paid
    PARTIALLY_PAID,   // Some amount paid
    PAID,             // Fully paid
    OVERDUE,          // Due date passed but not fully paid
    CANCELLED         // Invoice cancelled
}
