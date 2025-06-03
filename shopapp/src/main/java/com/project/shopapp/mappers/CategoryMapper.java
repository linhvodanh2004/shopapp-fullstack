package com.project.shopapp.mappers;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.responses.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryDTO categoryDTO);
    CategoryResponse toCategoryResponse(Category category);
    void updateCategory(@MappingTarget Category category, CategoryDTO categoryDTO);
}
