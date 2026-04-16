package com.inventory.service.impl;

import com.inventory.dto.WarehouseDTO;
import com.inventory.entity.User;
import com.inventory.entity.Warehouse;
import com.inventory.exceptions.UserNotFoundException;
import com.inventory.exceptions.WarehouseNotFoundException;
import com.inventory.mapper.WarehouseMapper;
import com.inventory.repository.UserRepository;
import com.inventory.repository.WarehouseRepository;
import com.inventory.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public WarehouseDTO createWarehouse(WarehouseDTO dto) {

        User manager = null;
        if (dto.getManagerId() != null) {
            manager = userRepository.findById(dto.getManagerId())
                    .orElseThrow(() -> new UserNotFoundException("Manager not found"));
        }

        Warehouse warehouse = WarehouseMapper.toEntity(dto, manager);

        return WarehouseMapper.toDTO(warehouseRepository.save(warehouse));
    }

    @Override
    @Transactional
    public WarehouseDTO updateWarehouse(Long id, WarehouseDTO dto) {

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found"));

        warehouse.setWarehouseName(dto.getWarehouseName());
        warehouse.setLocation(dto.getLocation());
        warehouse.setCapacity(dto.getCapacity());
        warehouse.setCapacityUnit(dto.getCapacityUnit());

        if (dto.getManagerId() != null) {
            User manager = userRepository.findById(dto.getManagerId())
                    .orElseThrow(() -> new UserNotFoundException("Manager not found"));
            warehouse.setManager(manager);
        }

        return WarehouseMapper.toDTO(warehouseRepository.save(warehouse));
    }

    @Override
    @Transactional(readOnly = true)
    public List<WarehouseDTO> getAllWarehouses() {
        return warehouseRepository.findAll()
                .stream()
                .map(WarehouseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public WarehouseDTO getWarehouseById(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found"));

        return WarehouseMapper.toDTO(warehouse);
    }
}