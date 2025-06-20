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
public class CategoryDTO {
    @NotEmpty(message = "Category name must not be empty")
    private String name;

    @JsonProperty("name_vie")
    @NotEmpty(message = "Category name in Vietnamese must not be empty")
    private String nameVie;
}
