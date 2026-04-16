package com.inventory.enums;

public enum ReferenceType {

    PURCHASE,     // From Purchase Order
    SALES,        // From Sales Order
    RETURN,       // From Sales/Purchase Return
    DAMAGE,       // Damaged stock
    ADJUSTMENT,   // Manual stock adjustment
    TRANSFER      // Warehouse transfer
}
