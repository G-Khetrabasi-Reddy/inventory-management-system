package com.inventory.service.impl;

import com.inventory.dto.DamageDTO;
import com.inventory.dto.StockMovementDTO;
import com.inventory.entity.*;
import com.inventory.exceptions.*;
import com.inventory.mapper.DamageMapper;
import com.inventory.repository.*;
import com.inventory.service.DamageService;
import com.inventory.service.StockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional  // IMP-06 bonus: ensures damage record + movement are atomic
public class DamageServiceImpl implements DamageService {

    private final DamageRepository damageRepo;
    private final ProductRepository productRepo;
    private final WarehouseRepository warehouseRepo;
    private final UserRepository userRepo;
    private final StockMovementRepository movementRepo;  // CRITICAL-11 FIX

    private final StockMovementService movementService;

    @Override
    public DamageDTO reportDamage(DamageDTO dto) {

        Product product = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + dto.getProductId()));
        Warehouse warehouse = warehouseRepo.findById(dto.getWarehouseId())
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found: " + dto.getWarehouseId()));
        User user = userRepo.findById(dto.getReportedBy())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + dto.getReportedBy()));

        // 🔥 Create stock movement (OUT)
        StockMovementDTO movementDTO = new StockMovementDTO();
        movementDTO.setProductId(dto.getProductId());
        movementDTO.setWarehouseId(dto.getWarehouseId());
        movementDTO.setQuantity(dto.getQuantity());
        movementDTO.setPerformedBy(dto.getReportedBy());

        var movement = movementService.handleDamage(movementDTO);

        DamageRecord record = new DamageRecord();
        record.setProduct(product);
        record.setWarehouse(warehouse);
        record.setQuantity(dto.getQuantity());
        record.setReason(dto.getReason());
        record.setReportedBy(user);

        // CRITICAL-11 FIX: Use getReferenceById() to get a managed JPA proxy
        // instead of creating a detached entity with just the ID set
        StockMovement movementEntity = movementRepo.getReferenceById(movement.getMovementId());
        record.setMovement(movementEntity);

        return DamageMapper.toDTO(damageRepo.save(record));
    }
}