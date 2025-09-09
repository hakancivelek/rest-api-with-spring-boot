package com.hakancivelek.product.dto;

import com.hakancivelek.product.validation.OneOfSkuOrBarcodeRequired;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
        String barcode
) {
}
