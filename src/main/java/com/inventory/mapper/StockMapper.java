package com.inventory.mapper;

import com.inventory.dto.StockDTO;
import com.inventory.entity.Product;
import com.inventory.entity.Stock;
import com.inventory.entity.Warehouse;

public class StockMapper {

    // 🔷 DTO → Entity
    public static Stock toEntity(StockDTO dto, Product product, Warehouse warehouse) {

        if (dto == null) return null;

        Stock stock = new Stock();

        stock.setStockId(dto.getStockId());
        stock.setProduct(product);
        stock.setWarehouse(warehouse);

        stock.setQuantityAvailable(dto.getQuantityAvailable());
        stock.setQuantityReserved(dto.getQuantityReserved());
        stock.setQuantityOnOrder(dto.getQuantityOnOrder());

        stock.setReorderLevelOverride(dto.getReorderLevelOverride());
        stock.setReorderQtyOverride(dto.getReorderQtyOverride());

        stock.setStockStatus(dto.getStockStatus());

        return stock;
    }

    // 🔷 Entity → DTO
    public static StockDTO toDTO(Stock entity) {

        if (entity == null) return null;

        StockDTO dto = new StockDTO();

        dto.setStockId(entity.getStockId());

        dto.setProductId(entity.getProduct().getProductId());
        dto.setWarehouseId(entity.getWarehouse().getWarehouseId());

        dto.setQuantityAvailable(entity.getQuantityAvailable());
        dto.setQuantityReserved(entity.getQuantityReserved());
        dto.setQuantityOnOrder(entity.getQuantityOnOrder());

        dto.setReorderLevelOverride(entity.getReorderLevelOverride());
        dto.setReorderQtyOverride(entity.getReorderQtyOverride());

        dto.setStockStatus(entity.getStockStatus());

        return dto;
    }
}