package com.inventory.service.impl;

import com.inventory.dto.AlertDTO;
import com.inventory.entity.Alert;
import com.inventory.entity.Product;
import com.inventory.entity.Stock;
import com.inventory.entity.User;
import com.inventory.enums.AlertType;
import com.inventory.enums.Priority;
import com.inventory.mapper.AlertMapper;
import com.inventory.repository.AlertRepository;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.StockRepository;
import com.inventory.repository.UserRepository;
import com.inventory.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final UserRepository userRepository;

    // 🔥 LOW STOCK ALERT
    @Override
    public void checkLowStock(Long productId, Long warehouseId) {

        Stock stock = stockRepository
                .findByProduct_ProductIdAndWarehouse_WarehouseId(productId, warehouseId)
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        Product product = stock.getProduct();

        // Override logic (Stock override > Product default)
        int reorderLevel = (stock.getReorderLevelOverride() != null)
                ? stock.getReorderLevelOverride()
                : product.getReorderLevel();

        if (stock.getQuantityAvailable() <= reorderLevel) {

            boolean exists = alertRepository
                    .existsByProduct_ProductIdAndWarehouse_WarehouseIdAndAlertTypeAndIsResolvedFalse(
                            productId, warehouseId, AlertType.LOW_STOCK
                    );

            if (!exists) {
                Alert alert = new Alert();
                alert.setProduct(product);
                alert.setWarehouse(stock.getWarehouse());
                alert.setAlertType(AlertType.LOW_STOCK);
                alert.setMessage("Low stock: " + product.getProductName());
                alert.setThresholdValue(reorderLevel);
                alert.setCurrentValue(stock.getQuantityAvailable());
                alert.setPriority(Priority.HIGH);

                alertRepository.save(alert);
            }
        }
    }

    // 🔥 OVERSTOCK ALERT
    @Override
    public void checkOverStock(Long productId, Long warehouseId) {

        Stock stock = stockRepository
                .findByProduct_ProductIdAndWarehouse_WarehouseId(productId, warehouseId)
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        Product product = stock.getProduct();

        int reorderLevel = (stock.getReorderLevelOverride() != null)
                ? stock.getReorderLevelOverride()
                : product.getReorderLevel();

        int maxThreshold = reorderLevel * 2;

        if (stock.getQuantityAvailable() > maxThreshold) {

            boolean exists = alertRepository
                    .existsByProduct_ProductIdAndWarehouse_WarehouseIdAndAlertTypeAndIsResolvedFalse(
                            productId, warehouseId, AlertType.OVERSTOCK
                    );

            if (!exists) {
                Alert alert = new Alert();
                alert.setProduct(product);
                alert.setWarehouse(stock.getWarehouse());
                alert.setAlertType(AlertType.OVERSTOCK);
                alert.setMessage("Overstock detected: " + product.getProductName());
                alert.setThresholdValue(maxThreshold);
                alert.setCurrentValue(stock.getQuantityAvailable());
                alert.setPriority(Priority.MEDIUM);

                alertRepository.save(alert);
            }
        }
    }

    // 🔥 EXPIRY ALERT
    @Override
    public void checkExpiry(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getExpiryDate() != null &&
                product.getExpiryDate().isBefore(LocalDate.now().plusDays(7))) {

            // Avoid duplicate expiry alerts (product-level check)
            boolean exists = alertRepository
                    .findByAlertTypeAndIsResolvedFalse(AlertType.EXPIRY)
                    .stream()
                    .anyMatch(a -> a.getProduct().getProductId().equals(productId));

            if (!exists) {
                Alert alert = new Alert();
                alert.setProduct(product);
                alert.setAlertType(AlertType.EXPIRY);
                alert.setMessage("Product nearing expiry: " + product.getProductName());
                alert.setThresholdValue(7);
                alert.setCurrentValue(0);
                alert.setPriority(Priority.HIGH);

                alertRepository.save(alert);
            }
        }
    }

    // 🔥 GET ACTIVE ALERTS
    @Override
    public List<AlertDTO> getActiveAlerts() {
        return alertRepository.findByIsResolvedFalse()
                .stream()
                .map(AlertMapper::toDTO)
                .collect(Collectors.toList());
    }

    // 🔥 FILTER ALERTS BY TYPE
    @Override
    public List<AlertDTO> getAlertsByType(AlertType type) {
        return alertRepository.findByAlertTypeAndIsResolvedFalse(type)
                .stream()
                .map(AlertMapper::toDTO)
                .collect(Collectors.toList());
    }

    // 🔥 RESOLVE ALERT
    @Override
    public AlertDTO resolveAlert(Long alertId, Long userId) {

        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        alert.setIsResolved(true);
        alert.setResolvedBy(user);
        alert.setResolvedAt(LocalDateTime.now());

        return AlertMapper.toDTO(alertRepository.save(alert));
    }
}