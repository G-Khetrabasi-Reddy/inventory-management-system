package com.inventory.mapper;

import com.inventory.dto.ProductDTO;
import com.inventory.entity.Category;
import com.inventory.entity.Product;

public class ProductMapper {

    // 🔷 DTO → Entity
    public static Product toEntity(ProductDTO dto, Category category) {

        Product product = new Product();

        product.setProductId(dto.getProductId());
        product.setProductName(dto.getProductName());
        product.setSku(dto.getSku());
        product.setCategory(category);

        product.setUnitPrice(dto.getUnitPrice());
        product.setCostPrice(dto.getCostPrice());

        product.setReorderLevel(dto.getReorderLevel());
        product.setReorderQuantity(dto.getReorderQuantity());

        product.setExpiryDate(dto.getExpiryDate());
        product.setUnitOfMeasure(dto.getUnitOfMeasure());

        product.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);

        return product;
    }

    // 🔷 Entity → DTO
    public static ProductDTO toDTO(Product product) {

        ProductDTO dto = new ProductDTO();

        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setSku(product.getSku());

        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getCategoryId());
        }

        dto.setUnitPrice(product.getUnitPrice());
        dto.setCostPrice(product.getCostPrice());

        dto.setReorderLevel(product.getReorderLevel());
        dto.setReorderQuantity(product.getReorderQuantity());

        dto.setExpiryDate(product.getExpiryDate());
        dto.setUnitOfMeasure(product.getUnitOfMeasure());

        dto.setIsActive(product.getIsActive());

        return dto;
    }
}