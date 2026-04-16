package com.inventory.exceptions;

public class InvalidPurchaseOperationException extends RuntimeException {
    public InvalidPurchaseOperationException(String message) {
        super(message);
    }
}
