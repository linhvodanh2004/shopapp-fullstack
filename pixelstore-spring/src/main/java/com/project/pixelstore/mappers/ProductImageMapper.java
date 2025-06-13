package com.project.pixelstore.mappers;

import com.project.pixelstore.dtos.ProductImageDTO;
import com.project.pixelstore.models.ProductImage;
import com.project.pixelstore.responses.ProductImageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {
    @Mapping(source = "productId", target = "product.id")
    ProductImage toProductImage(ProductImageDTO productImageDTO);

    @Mapping(source = "product.id", target = "productId")
    ProductImageResponse toProductImageResponse(ProductImage productImage);
}
