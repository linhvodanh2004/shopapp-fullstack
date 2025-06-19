package com.project.pixelstore.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.pixelstore.validations.email.ValidEmail;
import com.project.pixelstore.validations.password.ValidPassword;

import jakarta.validation.constraints.NotBlank;
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
public class UserDTO {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Full name is required")
    @JsonProperty("fullname")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @ValidEmail(message = "Invalid email address")
    private String email;

    @ValidPassword
    private String password;
    

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Postal code is required")
    private String postalCode;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    private boolean active;

    @JsonProperty("facebook_account_id")
    private int facebookAccountId;

    @JsonProperty("google_account_id")
    private int googleAccountId;

    @NotNull(message = "Role Id is required")
    @JsonProperty("role_id")
    private Long roleId;
}
