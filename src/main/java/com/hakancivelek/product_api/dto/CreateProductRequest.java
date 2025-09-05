package com.hakancivelek.product_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,
        @Min(value = 0, message = "Price must be non-negative")
        BigDecimal price
) {
}
