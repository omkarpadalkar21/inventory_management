package com.adv_java.inventory_management.services;

import com.adv_java.inventory_management.domain.dtos.category.*;
import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryById(UUID id);
    CategoryDto createCategory(CreateCategoryDto dto);
    CategoryDto updateCategory(UUID id, UpdateCategoryDto dto);
    void deleteCategory(UUID id);
}