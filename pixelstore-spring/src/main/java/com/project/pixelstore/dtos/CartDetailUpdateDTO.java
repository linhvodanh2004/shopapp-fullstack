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
public class CartDetailUpdateDTO {

    @NotEmpty(message = "Quantity must not be empty")
    private Integer quantity;

    @JsonProperty("calculated_price")
    private Float calculatedPrice;
}