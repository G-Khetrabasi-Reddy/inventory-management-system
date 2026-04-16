package com.inventory.service.impl;

import com.inventory.dto.SalesOrderDTO;
import com.inventory.dto.StockMovementDTO;
import com.inventory.entity.*;
import com.inventory.enums.*;
import com.inventory.exceptions.*;
import com.inventory.mapper.SalesOrderMapper;
import com.inventory.repository.*;
import com.inventory.service.SalesOrderService;
import com.inventory.service.StockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class SalesOrderServiceImpl implements SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final SalesOrderItemRepository itemRepository;
    private final StockMovementService stockMovementService;
    private final UserRepository userRepository;
    private final WarehouseRepository warehouseRepository;

    // ✅ 1. CREATE ORDER
    @Override
    public SalesOrderDTO createOrder(Long customerId, Long userId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + customerId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SalesOrder order = new SalesOrder();
        order.setCustomer(customer);
        order.setCreatedBy(user);
        order.setStatus(SalesStatus.PENDING);
        order.setTotalAmount(BigDecimal.ZERO);
        order.setItems(new ArrayList<>()); // Initialize the list to prevent NullPointerExceptions later

        salesOrderRepository.save(order);

        return SalesOrderMapper.toDTO(order);
    }

    // ✅ 2. ADD ITEM
    @Override
    public SalesOrderDTO addItem(Long soId, Long productId, int qty, BigDecimal price) {

        SalesOrder order = getOrder(soId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));

        if (itemRepository.existsBySalesOrderSoIdAndProductProductId(soId, productId)) {
            throw new RuntimeException("Product already exists in order");
        }

        SalesOrderItem item = new SalesOrderItem();
        item.setSalesOrder(order);
        item.setProduct(product);
        item.setQuantityOrdered(qty);
        item.setUnitPrice(price);

        // Ensure items list is initialized
        if (order.getItems() == null) {
            order.setItems(new ArrayList<>());
        }

        // ✅ FIX FOR ISSUE 8: Add the item to the order's tracked list
        order.getItems().add(item);

        BigDecimal itemTotal = price.multiply(BigDecimal.valueOf(qty));
        order.setTotalAmount(order.getTotalAmount().add(itemTotal));

        // ✅ FIX FOR ISSUE 8: Save only the parent SalesOrder.
        // CascadeType.ALL handles the database insertion of the SalesOrderItem automatically and efficiently!
        salesOrderRepository.save(order);

        return SalesOrderMapper.toDTO(order);
    }

    // ✅ 3. CONFIRM ORDER
    @Override
    public SalesOrderDTO confirmOrder(Long soId) {

        SalesOrder order = getOrder(soId);

        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new RuntimeException("Order has no items");
        }

        order.setStatus(SalesStatus.CONFIRMED);

        return SalesOrderMapper.toDTO(order);
    }

    // 🔥 4. RESERVE STOCK (available → reserved, no permanent deduction)
    @Override
    public SalesOrderDTO reserveStock(Long soId, Long warehouseId) {

        SalesOrder order = getOrder(soId);

        if (order.getStatus() != SalesStatus.CONFIRMED) {
            throw new RuntimeException("Order must be CONFIRMED first");
        }

        // Store the warehouse on the order so deliverOrder() knows where to dispatch from
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found: " + warehouseId));
        order.setWarehouse(warehouse);

        for (SalesOrderItem item : order.getItems()) {

            StockMovementDTO movementDTO = new StockMovementDTO();
            movementDTO.setProductId(item.getProduct().getProductId());
            movementDTO.setWarehouseId(warehouseId);
            movementDTO.setQuantity(item.getQuantityOrdered());
            movementDTO.setMovementType(MovementType.RESERVE);  // CRITICAL-04 FIX
            movementDTO.setReferenceType(ReferenceType.SALES);
            movementDTO.setReferenceId(order.getSoId());
            movementDTO.setPerformedBy(order.getCreatedBy().getUserId());

            stockMovementService.recordMovement(movementDTO);
        }

        order.setStatus(SalesStatus.RESERVED);

        return SalesOrderMapper.toDTO(order);
    }

    // 🚚 5. DELIVER ORDER (reserved → deducted via DISPATCH movement)
    @Override
    public SalesOrderDTO deliverOrder(Long soId) {

        SalesOrder order = getOrder(soId);

        if (order.getStatus() != SalesStatus.RESERVED) {
            throw new RuntimeException("Stock must be reserved first");
        }

        for (SalesOrderItem item : order.getItems()) {
            item.setQuantityDelivered(item.getQuantityOrdered());

            // CRITICAL-04 FIX: Create DISPATCH movement to deduct from reserved stock
            StockMovementDTO movementDTO = new StockMovementDTO();
            movementDTO.setProductId(item.getProduct().getProductId());
            movementDTO.setWarehouseId(order.getWarehouse().getWarehouseId());
            movementDTO.setQuantity(item.getQuantityOrdered());
            movementDTO.setMovementType(MovementType.DISPATCH);
            movementDTO.setReferenceType(ReferenceType.SALES);
            movementDTO.setReferenceId(order.getSoId());
            movementDTO.setPerformedBy(order.getCreatedBy().getUserId());

            stockMovementService.recordMovement(movementDTO);
        }

        order.setStatus(SalesStatus.DELIVERED);

        return SalesOrderMapper.toDTO(order);
    }

    // 🔧 HELPER
    private SalesOrder getOrder(Long id) {
        return salesOrderRepository.findById(id)
                .orElseThrow(() -> new SalesOrderNotFoundException("Sales Order not found: " + id));
    }
}