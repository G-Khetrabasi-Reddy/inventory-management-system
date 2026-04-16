package com.inventory.repository;

import com.inventory.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // 🔍 Find root categories (no parent)
    List<Category> findByParentCategoryIsNull();

    // 🔍 Find subcategories
    List<Category> findByParentCategoryCategoryId(Long parentId);

    // 🔍 Active categories
    List<Category> findByIsActiveTrue();
}