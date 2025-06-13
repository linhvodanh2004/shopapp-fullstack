package com.project.pixelstore.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageResponse {

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("product_id")
    private Long productId;
}
