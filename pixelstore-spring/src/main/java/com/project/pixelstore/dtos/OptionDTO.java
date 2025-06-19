package com.project.pixelstore.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class OptionDTO {
    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Name is required")
    @JsonProperty("name_vie")
    private String nameVie;

    @JsonProperty("product_id")
    @NotNull(message = "Product ID is required")
    private Long productId;
}
