package com.example.order.domain.cart.vo;

import com.example.shared.domain.DomainValueObject;

import java.math.BigDecimal;
import java.util.Currency;

public record Money(
        BigDecimal amount,
        Currency currency
) implements DomainValueObject {
}
