package com.project.shopapp.controllers;

import com.project.shopapp.components.LocalizationUtils;
import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.mappers.CategoryMapper;
import com.project.shopapp.models.Category;
import com.project.shopapp.responses.ApiResponse;
import com.project.shopapp.responses.CategoryResponse;
import com.project.shopapp.services.CategoryService;
import com.project.shopapp.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/v1/categories") // similar to other classes
@RequiredArgsConstructor

public class CategoryController {
    private final CategoryService categoryService;
    private final LocalizationUtils localizationUtils;
    private final CategoryMapper categoryMapper;

    @PostMapping("")
    public ApiResponse<?> createCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ApiResponse.builder()
                    .errors(errors)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("BAD REQUEST")
                    .build();
        }
        Category category = categoryService.createCategory(categoryDTO);
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(categoryMapper.toCategoryResponse(category))
                .message("OK")
                .build();
    }

    @GetMapping("")
    public ApiResponse<?> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
//        List<CategoryResponse> categoryResponses = categories.stream().map(
//                categoryMapper::toCategoryResponse
//        ).toList();
        List<CategoryResponse> categoryResponses = categories.stream().map(
                category -> CategoryResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build()
        ).toList();
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(categoryResponses)
                .message("OK")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<?> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult bindingResult
            ) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ApiResponse.builder()
                    .errors(errors)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("BAD REQUEST")
                    .build();
        }
        try {
            Category category = categoryService.updateCategory(id, categoryDTO);
            return ApiResponse.builder()
                    .data(categoryMapper.toCategoryResponse(category))
                    .message("OK")
                    .status(HttpStatus.OK.value())
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .errors(List.of(localizationUtils
                            .getLocalizedMessage(MessageKeys.UPDATE_CATEGORY_FAILED, e.getMessage())))
                    .message("BAD REQUEST")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("OK")
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("BAD REQUEST")
                    .errors(Collections.singletonList(localizationUtils.getLocalizedMessage(MessageKeys.DELETE_CATEGORY_FAILED, e.getMessage())))
                    .build();
        }
    }
}
