package com.example.order.domain.cart.vo;

import com.example.shared.domain.ValueObject;

public record CartDiscount(
        String couponCode,
        Money discountAmount
) implements ValueObject {
}
