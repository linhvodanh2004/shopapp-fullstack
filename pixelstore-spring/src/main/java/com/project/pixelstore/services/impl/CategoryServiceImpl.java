package com.project.pixelstore.services.impl;

import com.project.pixelstore.components.LocalizationUtils;
import com.project.pixelstore.dtos.CategoryDTO;
import com.project.pixelstore.exceptions.DataNotFoundException;
import com.project.pixelstore.mappers.CategoryMapper;
import com.project.pixelstore.models.Category;
import com.project.pixelstore.repositories.CategoryRepository;
import com.project.pixelstore.services.CategoryService;
import com.project.pixelstore.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final LocalizationUtils localizationUtils;
    private final CategoryMapper categoryMapper;

    @Override
    public Category createCategory(CategoryDTO category) {
        Category newCategory = categoryMapper.toCategory(category);
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(Long id) throws DataNotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(localizationUtils
                        .getLocalizedMessage(MessageKeys.CATEGORY_NOT_FOUND)));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public Category updateCategory(Long categoryId, CategoryDTO category) throws DataNotFoundException {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new DataNotFoundException(localizationUtils
                        .getLocalizedMessage(MessageKeys.CATEGORY_NOT_FOUND)));
        categoryMapper.updateCategory(existingCategory, category);
        return categoryRepository.save(existingCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) throws DataNotFoundException {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return;
        }
        throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.CATEGORY_NOT_FOUND));
    }
}
