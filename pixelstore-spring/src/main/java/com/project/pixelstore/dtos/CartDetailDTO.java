package com.project.pixelstore.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
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
public class CartDetailDTO {
    @JsonProperty("cart_id")
    @NotEmpty(message = "Cart id must not be empty")
    private Long cardId;

    @JsonProperty("product_ids")
    @NotEmpty(message = "Product ids must not be empty")
    private Long[] productIds;

    @NotEmpty(message = "Quantity must not be empty")
    private Integer quantity;

    @JsonProperty("option_ids")
    @NotEmpty(message = "Option ids must not be empty")
    private Long[] optionIds;

    @JsonProperty("option_value_ids")
    @NotEmpty(message = "Option value ids must not be empty")
    private Long[] optionValueIds;

    @JsonProperty("calculated_price")
    private Float calculatedPrice;
}
