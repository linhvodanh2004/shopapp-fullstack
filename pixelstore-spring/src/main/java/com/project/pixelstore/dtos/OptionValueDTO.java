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
public class OptionValueDTO {
    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Name is required")
    @JsonProperty("name_vie")
    private String nameVie;

    @JsonProperty("option_id")
    @NotNull(message = "Option ID is required")
    private Long optionId;
}
