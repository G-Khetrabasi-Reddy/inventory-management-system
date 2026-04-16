package com.inventory.enums;

public enum ReturnStatus {

    INITIATED,   // Return request created
    APPROVED,    // Return approved for processing
    REJECTED,    // Return rejected
    COMPLETED    // Return processed and stock updated
}
