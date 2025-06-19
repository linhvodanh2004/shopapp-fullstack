package com.project.pixelstore.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class ProductDTO {
    @NotBlank(message = "Product name is required")
    @Size(min = 5, max = 200, message = "Product name should have length from 5 to 200")
    private String name;

    @JsonProperty("name_vie")
    @NotBlank(message = "Product name is required")
    @Size(min = 5, max = 200, message = "Product name should have length from 5 to 200")
    private String nameVie;

    @Min(value = 0, message = "Price is greater than 0")
    private float price;

    @Min(value = 0, message = "Discount is greater than 0")
    private float discount;

    @Min(value = 0, message = "Quantity is greater than 0")
    private int quantity;

    @Min(value = 0, message = "Sold is greater than 0")
    private int sold;
    private String thumbnail;
    private String description;

    @JsonProperty("description_vie")
    private String descriptionVie;

    @JsonProperty("category_id") 
    private Long categoryId;

    @JsonProperty("active")
    private boolean active;
    
    @JsonProperty("product_images")
    private List<ProductImageDTO> productImages;

    @JsonProperty("options")
    private List<OptionDTO> options;
}
