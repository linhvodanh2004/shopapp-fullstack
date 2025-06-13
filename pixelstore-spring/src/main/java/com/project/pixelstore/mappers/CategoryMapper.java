package com.project.pixelstore.mappers;

import com.project.pixelstore.dtos.CategoryDTO;
import com.project.pixelstore.models.Category;
import com.project.pixelstore.responses.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryDTO categoryDTO);
    CategoryResponse toCategoryResponse(Category category);
    void updateCategory(@MappingTarget Category category, CategoryDTO categoryDTO);
}
