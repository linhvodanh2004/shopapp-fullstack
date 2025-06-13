package com.project.pixelstore.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.pixelstore.models.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterResponse {
    @JsonProperty("user")
    private User user;
}
