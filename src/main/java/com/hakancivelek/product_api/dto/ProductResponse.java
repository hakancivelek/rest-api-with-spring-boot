package com.hakancivelek.product_api.dto;

public record ProductResponse(
        String id,
        String name,
        double price
) {
}
