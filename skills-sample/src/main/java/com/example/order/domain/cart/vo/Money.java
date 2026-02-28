package com.example.order.domain.cart.vo;

import com.example.shared.domain.ValueObject;

import java.math.BigDecimal;
import java.util.Currency;

public record Money(
        BigDecimal amount,
        Currency currency
) implements ValueObject {
}
