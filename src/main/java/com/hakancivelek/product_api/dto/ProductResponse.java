package com.hakancivelek.product_api.dto;

import java.math.BigDecimal;

public record ProductResponse(String id,
                              String name,
                              BigDecimal price
) {
}
