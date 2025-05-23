package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.ProductImage;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("product_id")
    private Long productId;
}
