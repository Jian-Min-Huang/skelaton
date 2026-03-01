package com.example.inventory.domain.product.vo;

import com.example.shared.domain.DomainValueObject;

public record ProductSpec(
        String brand,
        String model,
        Double weight,
        String weightUnit,
        String dimensions
) implements DomainValueObject {
}
