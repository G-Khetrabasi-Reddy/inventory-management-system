package com.inventory.enums;

public enum ReportType {

    SALES,            // Sales performance reports
    PURCHASE,         // Purchase activity reports
    INVENTORY,        // Current stock status
    STOCK_MOVEMENT,   // Movement history (IN/OUT/TRANSFER)
    LOW_STOCK,        // Products below reorder level
    EXPIRY,           // Products nearing expiry
    DAMAGE            // Damaged stock reports
}
