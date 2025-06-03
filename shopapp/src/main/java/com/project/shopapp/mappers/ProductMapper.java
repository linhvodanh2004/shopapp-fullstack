package com.project.shopapp.mappers;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.models.Product;
import com.project.shopapp.responses.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductDTO productDTO);

    @Mapping(source = "category.id", target = "categoryId")
    ProductResponse toProductResponse(Product product);

    void updateProduct(@MappingTarget Product product, ProductDTO productDTO);
}
