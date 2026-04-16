package com.inventory.mapper;

import com.inventory.dto.CategoryDTO;
import com.inventory.entity.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    // 🔷 DTO → Entity
    public static Category toEntity(CategoryDTO dto, Category parent) {

        Category category = new Category();

        category.setCategoryId(dto.getCategoryId());
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());
        category.setParentCategory(parent);

        category.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);

        return category;
    }

    // 🔷 Entity → DTO (Recursive)
    public static CategoryDTO toDTO(Category category) {

        CategoryDTO dto = new CategoryDTO();

        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        dto.setDescription(category.getDescription());

        if (category.getParentCategory() != null) {
            dto.setParentCategoryId(category.getParentCategory().getCategoryId());
        }

        dto.setIsActive(category.getIsActive());

        // 🔥 Recursive mapping
        if (category.getSubCategories() != null) {
            List<CategoryDTO> subDTOs = category.getSubCategories()
                    .stream()
                    .map(CategoryMapper::toDTO)
                    .collect(Collectors.toList());

            dto.setSubCategories(subDTOs);
        }

        return dto;
    }
}