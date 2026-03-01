package com.example.inventory.domain.product.vo;

import com.example.shared.domain.DomainValueObject;

import java.math.BigDecimal;
import java.util.Currency;

public record Money(
        BigDecimal amount,
        Currency currency
) implements DomainValueObject {
}
