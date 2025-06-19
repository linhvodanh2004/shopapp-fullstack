package com.project.pixelstore.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.pixelstore.validations.email.ValidEmail;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data

public class OrderDTO {
    @JsonProperty("user_id")
    @Min(value = 1, message = "User id must be greater than 0")
    private Long userId;

    @JsonProperty("fullname")
    private String fullName;

    @ValidEmail
    private String email;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[0-9]{10,13}$", message = "Phone number is invalid")
    private String phoneNumber;

    private String address;
    private String state;
    private String city;
    private String country;

    @JsonProperty("postal_code")
    private String postalCode;

    private String note;
    private String status;

    @JsonProperty("calculated_price")
    private Float calculatedPrice;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("tracking_number")
    private String trackingNumber;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("shipping_fee")
    private Float shippingFee;

    @JsonProperty("coupon_id")
    private Long couponId;
}
