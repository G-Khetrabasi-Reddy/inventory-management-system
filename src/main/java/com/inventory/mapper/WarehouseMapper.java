package com.inventory.mapper;

import com.inventory.dto.WarehouseDTO;
import com.inventory.entity.User;
import com.inventory.entity.Warehouse;

public class WarehouseMapper {

    // 🔷 DTO → Entity
    public static Warehouse toEntity(WarehouseDTO dto, User manager) {

        if (dto == null) return null;

        Warehouse warehouse = new Warehouse();

        warehouse.setWarehouseId(dto.getWarehouseId());
        warehouse.setWarehouseName(dto.getWarehouseName());
        warehouse.setLocation(dto.getLocation());
        warehouse.setManager(manager);
        warehouse.setCapacity(dto.getCapacity());
        warehouse.setCapacityUnit(dto.getCapacityUnit());

        return warehouse;
    }

    // 🔷 Entity → DTO
    public static WarehouseDTO toDTO(Warehouse entity) {

        if (entity == null) return null;

        WarehouseDTO dto = new WarehouseDTO();

        dto.setWarehouseId(entity.getWarehouseId());
        dto.setWarehouseName(entity.getWarehouseName());
        dto.setLocation(entity.getLocation());

        if (entity.getManager() != null) {
            dto.setManagerId(entity.getManager().getUserId());
        }

        dto.setCapacity(entity.getCapacity());
        dto.setCapacityUnit(entity.getCapacityUnit());

        return dto;
    }
}