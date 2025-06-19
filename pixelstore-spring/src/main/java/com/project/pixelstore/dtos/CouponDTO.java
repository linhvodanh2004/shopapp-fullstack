package com.project.pixelstore.dtos;

import java.time.LocalDateTime;

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
public class CouponDTO {
    private String description;

    @JsonProperty("descripton_vie")
    private String descriptonVie;

    @JsonProperty("started_at")
    @NotNull(message = "Started date is required")
    private LocalDateTime startedAt;

    @JsonProperty("ended_at")
    @NotNull(message = "End date is required")
    private LocalDateTime endedAt;

    @NotEmpty(message = "Coupon type is required")
    private String type;

    @NotNull(message = "Coupon value is required")
    private Float value;

    @JsonProperty("max_usage")
    @NotNull(message = "Max usage is required")
    private Integer maxUsage;

    @JsonProperty("order_money_min")
    private Float orderMoneyMin;

    @JsonProperty("order_sale_max")
    private Float orderSaleMax;

    @JsonProperty("product_ids")
    private Long[] productIds;
}
