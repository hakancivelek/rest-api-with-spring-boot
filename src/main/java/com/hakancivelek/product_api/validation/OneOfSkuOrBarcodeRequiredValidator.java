package com.hakancivelek.product_api.validation;

import com.hakancivelek.product_api.dto.CreateProductRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OneOfSkuOrBarcodeRequiredValidator implements ConstraintValidator<OneOfSkuOrBarcodeRequired, CreateProductRequest> {

    @Override
    public boolean isValid(CreateProductRequest createProductRequest, ConstraintValidatorContext context) {
        if (createProductRequest == null) return false;
        return (createProductRequest.sku() != null && !createProductRequest.sku().isBlank()) ||
                (createProductRequest.barcode() != null && !createProductRequest.barcode().isBlank());
    }
}
