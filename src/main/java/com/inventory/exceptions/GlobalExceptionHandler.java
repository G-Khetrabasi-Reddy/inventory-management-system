package com.inventory.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🔷 Domain Not Found Exceptions (Grouped together)
    @ExceptionHandler({
            UserNotFoundException.class,
            ProductNotFoundException.class,
            CategoryNotFoundException.class,
            SupplierNotFoundException.class,
            WarehouseNotFoundException.class,
            StockNotFoundException.class,
            PurchaseOrderNotFoundException.class,
            SalesOrderNotFoundException.class,
            CustomerNotFoundException.class,
            InvoiceNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleDomainNotFound(RuntimeException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    // 🔷 Duplicate entity / Business Rule Violation
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleUserExists(UserAlreadyExistsException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 🔷 Purchase Module Business Rule Violations ✅
    @ExceptionHandler({
            InvalidPurchaseOperationException.class,
            PaymentFailedException.class,
            PurchaseReturnException.class,
            InsufficientStockException.class,
            ReturnOrderException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handlePurchaseErrors(RuntimeException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 🔷 Invalid credentials
    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, Object> handleInvalidCredentials(InvalidCredentialsException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    // 🔷 Generic exception fallback
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleGeneral(Exception ex) {
        return buildResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationExceptions(org.springframework.web.bind.MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return buildResponse(message, HttpStatus.BAD_REQUEST);
    }

    // 🔷 Common response builder
    private Map<String, Object> buildResponse(String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", message);
        return response;
    }
}