package com.inventory.repository;

import com.inventory.entity.StockMovement;
import com.inventory.enums.MovementType;
import com.inventory.enums.ReferenceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    // 🔍 Get all movements for a product
    List<StockMovement> findByProduct_ProductId(Long productId);

    // 🔍 Get all movements for a warehouse
    List<StockMovement> findByWarehouse_WarehouseId(Long warehouseId);

    // 🔍 Filter by movement type (IN / OUT / TRANSFER)
    List<StockMovement> findByMovementType(MovementType movementType);

    // 🔍 Product + Warehouse combined
    List<StockMovement> findByProduct_ProductIdAndWarehouse_WarehouseId(
            Long productId,
            Long warehouseId
    );

    // 🔍 Reference tracking (VERY IMPORTANT)
    List<StockMovement> findByReferenceTypeAndReferenceId(
            ReferenceType referenceType,
            Long referenceId
    );
}