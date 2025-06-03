package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.Category;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
}
