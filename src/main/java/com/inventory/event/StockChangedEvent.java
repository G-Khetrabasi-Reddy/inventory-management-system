package com.inventory.event;

import com.inventory.enums.MovementType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class StockChangedEvent extends ApplicationEvent {

    private final Long productId;
    private final Long warehouseId;
    private final Long destinationWarehouseId;
    private final MovementType movementType;

    public StockChangedEvent(Object source, Long productId, Long warehouseId,
                             Long destinationWarehouseId, MovementType movementType) {
        super(source);
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.destinationWarehouseId = destinationWarehouseId;
        this.movementType = movementType;
    }
}
