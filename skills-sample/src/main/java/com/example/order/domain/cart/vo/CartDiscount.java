package com.example.order.domain.cart.vo;

import com.example.shared.domain.DomainValueObject;

public record CartDiscount(
        String couponCode,
        Money discountAmount
) implements DomainValueObject {
}
