package com.inventory.service.impl;

import com.inventory.dto.StockDTO;
import com.inventory.entity.Product;
import com.inventory.entity.Stock;
import com.inventory.entity.Warehouse;
import com.inventory.exceptions.ProductNotFoundException;
import com.inventory.exceptions.StockNotFoundException;
import com.inventory.exceptions.WarehouseNotFoundException;
import com.inventory.mapper.StockMapper;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.StockRepository;
import com.inventory.repository.WarehouseRepository;
import com.inventory.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    // 🔥 CORE FETCH
    private Stock getStockEntity(Long productId, Long warehouseId) {
        return stockRepository
                .findByProduct_ProductIdAndWarehouse_WarehouseId(productId, warehouseId)
                .orElseThrow(() -> new StockNotFoundException("Stock not found"));
    }

    @Override
    public StockDTO getStock(Long productId, Long warehouseId) {
        return StockMapper.toDTO(getStockEntity(productId, warehouseId));
    }

    @Override
    public List<StockDTO> getAllStockByProduct(Long productId) {
        return stockRepository.findByProduct_ProductId(productId)
                .stream()
                .map(StockMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockDTO> getAllStockByWarehouse(Long warehouseId) {
        return stockRepository.findByWarehouse_WarehouseId(warehouseId)
                .stream()
                .map(StockMapper::toDTO)
                .collect(Collectors.toList());
    }

    // 🔥 CREATE STOCK IF NOT EXISTS
    @Override
    @Transactional
    public void createStockIfNotExists(Long productId, Long warehouseId) {

        boolean exists = stockRepository
                .existsByProduct_ProductIdAndWarehouse_WarehouseId(productId, warehouseId);

        if (!exists) {

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            Warehouse warehouse = warehouseRepository.findById(warehouseId)
                    .orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found"));

            Stock stock = new Stock();
            stock.setProduct(product);
            stock.setWarehouse(warehouse);

            stock.setQuantityAvailable(0);
            stock.setQuantityReserved(0);
            stock.setQuantityOnOrder(0);

            stockRepository.save(stock);
        }
    }

    // 🔥 INTERNAL UPDATE
    @Override
    @Transactional
    public void updateStockQuantities(Long productId, Long warehouseId,
                                      int available, int reserved, int onOrder) {

        Stock stock = getStockEntity(productId, warehouseId);

        if (available < 0 || reserved < 0 || onOrder < 0) {
            throw new RuntimeException("Stock cannot be negative");
        }

        stock.setQuantityAvailable(available);
        stock.setQuantityReserved(reserved);
        stock.setQuantityOnOrder(onOrder);

        stockRepository.save(stock);
    }

    // 🔥 RESERVE STOCK (Order placed)
    @Override
    @Transactional
    public void reserveStock(Long productId, Long warehouseId, int quantity) {

        Stock stock = getStockEntity(productId, warehouseId);

        if (stock.getQuantityAvailable() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        stock.setQuantityAvailable(stock.getQuantityAvailable() - quantity);
        stock.setQuantityReserved(stock.getQuantityReserved() + quantity);
    }

    // 🔥 RELEASE STOCK (Cancel)
    @Override
    @Transactional
    public void releaseStock(Long productId, Long warehouseId, int quantity) {

        Stock stock = getStockEntity(productId, warehouseId);

        stock.setQuantityReserved(stock.getQuantityReserved() - quantity);
        stock.setQuantityAvailable(stock.getQuantityAvailable() + quantity);
    }

    // 🔥 ADD STOCK (Purchase / Return)
    @Override
    @Transactional
    public void addStock(Long productId, Long warehouseId, int quantity) {

        Stock stock = getStockEntity(productId, warehouseId);

        stock.setQuantityAvailable(stock.getQuantityAvailable() + quantity);
        stock.setQuantityOnOrder(Math.max(0, stock.getQuantityOnOrder() - quantity));
    }

    // 🔥 DEDUCT RESERVED STOCK (Sale completed)
    @Override
    @Transactional
    public void deductStock(Long productId, Long warehouseId, int quantity) {

        Stock stock = getStockEntity(productId, warehouseId);

        if (stock.getQuantityReserved() < quantity) {
            throw new RuntimeException("Invalid deduction");
        }

        stock.setQuantityReserved(stock.getQuantityReserved() - quantity);
    }

    // 🔥 NEW: DIRECT REDUCTION (Damage / Transfer)
    @Override
    @Transactional
    public void reduceAvailableStock(Long productId, Long warehouseId, int quantity) {

        Stock stock = getStockEntity(productId, warehouseId);

        if (stock.getQuantityAvailable() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        stock.setQuantityAvailable(stock.getQuantityAvailable() - quantity);
    }

    // Add the implementation
    @Override
    @Transactional
    public void addQuantityOnOrder(Long productId, Long warehouseId, int quantity) {
        // Ensure the stock record exists first
        createStockIfNotExists(productId, warehouseId);

        Stock stock = getStockEntity(productId, warehouseId);
        stock.setQuantityOnOrder(stock.getQuantityOnOrder() + quantity);

        stockRepository.save(stock);
    }
}