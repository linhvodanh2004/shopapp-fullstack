package com.project.shopapp.services;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(Long id) throws DataNotFoundException;
    List<Category> getAllCategories();
    Category updateCategory(Long categoryId, CategoryDTO category) throws DataNotFoundException;
    void deleteCategory(Long id) throws DataNotFoundException;
}
