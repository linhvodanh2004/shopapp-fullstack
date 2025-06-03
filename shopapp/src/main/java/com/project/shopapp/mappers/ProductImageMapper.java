package com.project.shopapp.mappers;

import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.responses.ProductImageResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {
    ProductImage toProductImage(ProductImageDTO productImageDTO);
    ProductImageResponse toProductImageResponse(ProductImage productImage);
}
