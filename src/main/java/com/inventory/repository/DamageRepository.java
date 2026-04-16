package com.inventory.repository;

import com.inventory.entity.DamageRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DamageRepository extends JpaRepository<DamageRecord, Long> {

    List<DamageRecord> findByProduct_ProductId(Long productId);

    List<DamageRecord> findByWarehouse_WarehouseId(Long warehouseId);

}