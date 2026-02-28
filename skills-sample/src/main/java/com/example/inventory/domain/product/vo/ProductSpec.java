package com.example.inventory.domain.product.vo;

import com.example.shared.domain.ValueObject;

public record ProductSpec(
        String brand,
        String model,
        Double weight,
        String weightUnit,
        String dimensions
) implements ValueObject {
}
