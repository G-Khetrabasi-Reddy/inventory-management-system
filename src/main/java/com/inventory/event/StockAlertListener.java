package com.inventory.event;

import com.inventory.enums.MovementType;
import com.inventory.service.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockAlertListener {

    private final AlertService alertService;

    /**
     * Fires ONLY after the stock movement transaction has committed successfully.
     * Runs asynchronously so it doesn't block the caller.
     *
     * CRITICAL-02 fix: Ensures alerts are never created for rolled-back transactions,
     * and the alert check runs in a proper Spring-managed thread with its own
     * persistence context (no LazyInitializationException).
     */
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onStockChanged(StockChangedEvent event) {
        try {
            // Check primary warehouse alerts
            alertService.checkLowStock(event.getProductId(), event.getWarehouseId());
            alertService.checkOverStock(event.getProductId(), event.getWarehouseId());

            // If it was a transfer, also verify the destination warehouse
            if (event.getMovementType() == MovementType.TRANSFER
                    && event.getDestinationWarehouseId() != null) {
                alertService.checkLowStock(event.getProductId(), event.getDestinationWarehouseId());
                alertService.checkOverStock(event.getProductId(), event.getDestinationWarehouseId());
            }
        } catch (Exception e) {
            log.error("Failed to process stock alert for product={}, warehouse={}",
                    event.getProductId(), event.getWarehouseId(), e);
        }
    }
}
