package com.project.pixelstore.services;

import com.project.pixelstore.dtos.CategoryDTO;
import com.project.pixelstore.exceptions.DataNotFoundException;
import com.project.pixelstore.models.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(Long id) throws DataNotFoundException;
    List<Category> getAllCategories();
    Category updateCategory(Long categoryId, CategoryDTO category) throws DataNotFoundException;
    void deleteCategory(Long id) throws DataNotFoundException;
}
