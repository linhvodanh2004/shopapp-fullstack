package com.project.pixelstore.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class ProductImageDTO {

    @Size(min = 5, max = 200, message = "Image url must be between 5 and 200.")
    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("product_id")
    @Min(value = 1, message = "Product id must be greater than 0")
    private Long productId;
}
