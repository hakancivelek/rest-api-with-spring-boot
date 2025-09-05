package com.hakancivelek.product_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record UpdateRequestBody(
        @NotBlank(message = "Name cannot be blank")
        String name,
        @Min(value = 0, message = "Price must be greater than or equal to zero")
        BigDecimal price
) {
}
