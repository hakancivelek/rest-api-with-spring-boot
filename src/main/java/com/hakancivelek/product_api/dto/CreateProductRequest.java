package com.hakancivelek.product_api.dto;

import com.hakancivelek.product_api.validation.OneOfSkuOrBarcodeRequired;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@OneOfSkuOrBarcodeRequired
public record CreateProductRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,
        @Min(value = 0, message = "Price must be non-negative")
        BigDecimal price,
        @Size(max = 250, message = "Description cannot exceed 250 characters")
        String description,
        String sku,
        String barcode,
        @NotNull(message = "Category is required") @Valid CategoryRequest category
) {
}
