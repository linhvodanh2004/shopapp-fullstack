package com.project.pixelstore.mappers;

import com.project.pixelstore.dtos.ProductDTO;
import com.project.pixelstore.models.Product;
import com.project.pixelstore.responses.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "categoryId", target = "category.id")
    Product toProduct(ProductDTO productDTO);

    @Mapping(source = "category.id", target = "categoryId")
    ProductResponse toProductResponse(Product product);

    @Mapping(source = "categoryId", target = "category.id")
    void updateProduct(@MappingTarget Product product, ProductDTO productDTO);
}
