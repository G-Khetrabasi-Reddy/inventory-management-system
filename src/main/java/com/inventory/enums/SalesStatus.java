package com.inventory.enums;

public enum SalesStatus {

    PENDING,     // Order created
    CONFIRMED,   // Validated & ready
    RESERVED,    // Stock reserved
    SHIPPED,     // Sent for delivery
    DELIVERED,   // Completed
    CANCELLED    // Cancelled
}