package com.inventory.mapper;

import com.inventory.dto.ProductSupplierDTO;
import com.inventory.entity.Product;
import com.inventory.entity.ProductSupplier;
import com.inventory.entity.Supplier;

public class ProductSupplierMapper {

    // 🔷 DTO → Entity
    public static ProductSupplier toEntity(ProductSupplierDTO dto,
                                           Product product,
                                           Supplier supplier) {

        ProductSupplier ps = new ProductSupplier();

        ps.setProduct(product);
        ps.setSupplier(supplier);

        ps.setSupplierProductCode(dto.getSupplierProductCode());
        ps.setUnitCost(dto.getUnitCost());
        ps.setLeadTimeDays(dto.getLeadTimeDays());

        ps.setIsPreferred(dto.getIsPreferred() != null ? dto.getIsPreferred() : false);
        ps.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);

        return ps;
    }

    // 🔷 Entity → DTO
    public static ProductSupplierDTO toDTO(ProductSupplier ps) {

        ProductSupplierDTO dto = new ProductSupplierDTO();

        dto.setProductId(ps.getProduct().getProductId());
        dto.setSupplierId(ps.getSupplier().getSupplierId());

        dto.setSupplierProductCode(ps.getSupplierProductCode());
        dto.setUnitCost(ps.getUnitCost());
        dto.setLeadTimeDays(ps.getLeadTimeDays());

        dto.setIsPreferred(ps.getIsPreferred());
        dto.setIsActive(ps.getIsActive());

        return dto;
    }
}