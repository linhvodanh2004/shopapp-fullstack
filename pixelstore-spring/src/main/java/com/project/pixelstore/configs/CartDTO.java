package com.project.pixelstore.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class CartDTO {
    @JsonProperty("user_id")
    @NotEmpty(message = "User id must not be empty")
    private Long userId;

    @JsonProperty("calculated_price")
    private Float calculatedPrice;
}
