package com.inventory.service.impl;

import com.inventory.dto.ReturnOrderDTO;
import com.inventory.dto.StockMovementDTO;
import com.inventory.entity.*;
import com.inventory.enums.*;
import com.inventory.exceptions.*;
import com.inventory.mapper.ReturnOrderMapper;
import com.inventory.repository.*;
import com.inventory.service.ReturnOrderService;
import com.inventory.service.StockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional  // CRITICAL-07 FIX: ensures return save + stock movement are atomic
public class ReturnOrderServiceImpl implements ReturnOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final ProductRepository productRepository;
    private final ReturnOrderRepository returnRepository;
    private final WarehouseRepository warehouseRepository;  // CRITICAL-08 FIX
    private final StockMovementService stockMovementService;

    @Override
    public ReturnOrderDTO processReturn(Long soId, Long productId, Long warehouseId, int qty, String reason) {

        SalesOrder order = salesOrderRepository.findById(soId)
                .orElseThrow(() -> new SalesOrderNotFoundException("SO not found: " + soId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));

        // CRITICAL-08 FIX: look up and set the warehouse (entity has nullable = false)
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found: " + warehouseId));

        ReturnOrder r = new ReturnOrder();
        r.setSalesOrder(order);
        r.setProduct(product);
        r.setWarehouse(warehouse);  // CRITICAL-08 FIX: was missing, caused DB constraint violation
        r.setQuantity(qty);
        r.setReason(reason);
        r.setStatus(ReturnStatus.INITIATED);

        returnRepository.save(r);

        // 🔥 STOCK MOVEMENT (IN) — returned goods go back into available stock
        StockMovementDTO movementDTO = new StockMovementDTO();
        movementDTO.setProductId(productId);
        movementDTO.setWarehouseId(warehouseId);
        movementDTO.setQuantity(qty);
        movementDTO.setMovementType(MovementType.IN);
        movementDTO.setReferenceType(ReferenceType.RETURN);
        movementDTO.setReferenceId(r.getReturnId());
        movementDTO.setPerformedBy(order.getCreatedBy().getUserId());

        stockMovementService.recordMovement(movementDTO);

        r.setStatus(ReturnStatus.COMPLETED);

        return ReturnOrderMapper.toDTO(r);
    }
}