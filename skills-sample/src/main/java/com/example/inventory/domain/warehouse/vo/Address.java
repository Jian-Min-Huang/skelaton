package com.example.inventory.domain.warehouse.vo;

import com.example.shared.domain.ValueObject;

public record Address(
        String city,
        String district,
        String street,
        String zipCode
) implements ValueObject {
}
