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
public class OptionUpdateDTO {
    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Name is required")
    @JsonProperty("name_vie")
    private String nameVie;
}
