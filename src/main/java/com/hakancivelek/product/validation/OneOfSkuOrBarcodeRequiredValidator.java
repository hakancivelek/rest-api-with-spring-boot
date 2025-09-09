package com.hakancivelek.product.validation;

import com.hakancivelek.product.dto.CreateProductRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OneOfSkuOrBarcodeRequiredValidator implements ConstraintValidator<OneOfSkuOrBarcodeRequired, CreateProductRequest> {

    @Override
    public boolean isValid(CreateProductRequest createProductRequest, ConstraintValidatorContext context) {
        if (createProductRequest == null) return false;
        boolean hasSku = createProductRequest.sku() != null && !createProductRequest.sku().isBlank();
        boolean hasBarcode = createProductRequest.barcode() != null && !createProductRequest.barcode().isBlank();

        return hasSku || hasBarcode;
    }
}
