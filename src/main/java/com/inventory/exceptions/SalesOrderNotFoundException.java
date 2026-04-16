package com.inventory.exceptions;

public class SalesOrderNotFoundException extends RuntimeException {
    public SalesOrderNotFoundException(String message) {
        super(message);
    }
}
