package com.project.pixelstore.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.pixelstore.models.Option;
import com.project.pixelstore.models.OptionValue;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class OrderDetailDTO {

    @JsonProperty("order_id")
    @Min(value = 1, message = "Order id must be greater than 0")
    private Long orderId;

    @JsonProperty("product_id")
    @Min(value = 1, message = "Product id must be greater than 0")
    private Long productId;

    @Min(value = 0, message = "Price must be greater than or equal 0")
    @JsonProperty("calculated_price")
    private Float calculatedPrice;

    @Min(value = 1, message = "Quantity  must be greater than 0")
    private int quantity;

    @JsonProperty("option_id")
    private Long option;

    @JsonProperty("option_value_id")
    private Long optionValue;
}
