package com.inventory.service;

import com.inventory.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO dto);

    List<CategoryDTO> getCategoryHierarchy();

    CategoryDTO updateCategory(Long id, CategoryDTO dto);
}