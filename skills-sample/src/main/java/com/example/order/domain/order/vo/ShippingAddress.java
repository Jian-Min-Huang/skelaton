package com.example.order.domain.order.vo;

import com.example.shared.domain.ValueObject;

public record ShippingAddress(
        String recipientName,
        String phone,
        String city,
        String district,
        String street,
        String zipCode
) implements ValueObject {
}
