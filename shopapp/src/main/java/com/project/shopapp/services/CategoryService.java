package com.project.shopapp.services;

import com.project.shopapp.components.LocalizationUtils;
import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Category;
import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final LocalizationUtils localizationUtils;

    @Override
    public Category createCategory(CategoryDTO category) {
        Category newCategory = Category
                .builder()
                .name(category.getName())
                .build();
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
    public Category updateCategory(Long categoryId, CategoryDTO category) throws DataNotFoundException {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new DataNotFoundException(localizationUtils
                        .getLocalizedMessage(MessageKeys.CATEGORY_NOT_FOUND)));
        existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) throws DataNotFoundException {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return;
        }
        throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.CATEGORY_NOT_FOUND));
    }
}
