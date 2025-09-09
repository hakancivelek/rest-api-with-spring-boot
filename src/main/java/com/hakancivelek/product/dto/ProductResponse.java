package com.hakancivelek.product.dto;

import java.math.BigDecimal;

public record ProductResponse(Long id,
                              String name,
                              BigDecimal price,
                              String description,
                              String sku,
                              String barcode
) {
}
