package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginResponse {
    @JsonProperty("token")
    private String token;
}
