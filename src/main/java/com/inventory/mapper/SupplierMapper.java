package com.inventory.mapper;

import com.inventory.dto.SupplierDTO;
import com.inventory.entity.Supplier;

public class SupplierMapper {

    // 🔷 DTO → Entity
    public static Supplier toEntity(SupplierDTO dto) {

        Supplier supplier = new Supplier();

        supplier.setSupplierId(dto.getSupplierId());
        supplier.setSupplierName(dto.getSupplierName());
        supplier.setContactPerson(dto.getContactPerson());
        supplier.setPhone(dto.getPhone());
        supplier.setEmail(dto.getEmail());
        supplier.setAddress(dto.getAddress());

        supplier.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);

        return supplier;
    }

    // 🔷 Entity → DTO
    public static SupplierDTO toDTO(Supplier supplier) {

        SupplierDTO dto = new SupplierDTO();

        dto.setSupplierId(supplier.getSupplierId());
        dto.setSupplierName(supplier.getSupplierName());
        dto.setContactPerson(supplier.getContactPerson());
        dto.setPhone(supplier.getPhone());
        dto.setEmail(supplier.getEmail());
        dto.setAddress(supplier.getAddress());

        dto.setIsActive(supplier.getIsActive());

        return dto;
    }
}