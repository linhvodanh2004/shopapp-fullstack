package com.project.pixelstore.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.pixelstore.models.Product;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
//@NoArgsConstructor
@Builder
public class ProductResponse{
    private Long id;
    private String name;
    private float price;
    private String thumbnail;
    private String description;
    @JsonProperty("category_id") // Use JsonProperty for mapping to database
    private Long categoryId;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public static ProductResponse fromProduct(Product product){
        ProductResponse productResponse = ProductResponse
                .builder()
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .thumbnail(product.getThumbnail())
                .categoryId(product.getCategory().getId())
                .build();
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }
}
