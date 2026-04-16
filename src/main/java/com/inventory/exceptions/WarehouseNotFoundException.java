package com.inventory.exceptions;

public class WarehouseNotFoundException extends RuntimeException {
    public WarehouseNotFoundException(String message) {
        super(message);
    }
}
