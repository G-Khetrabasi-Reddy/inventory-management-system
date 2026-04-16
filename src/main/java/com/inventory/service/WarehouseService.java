package com.inventory.service;

import com.inventory.dto.WarehouseDTO;

import java.util.List;

public interface WarehouseService {

    WarehouseDTO createWarehouse(WarehouseDTO dto);

    WarehouseDTO updateWarehouse(Long id, WarehouseDTO dto);

    List<WarehouseDTO> getAllWarehouses();

    WarehouseDTO getWarehouseById(Long id);
}