package com.example.order.usecase.query.output;

import com.example.shared.domain.CqrsQueryOutput;

import java.math.BigDecimal;
import java.util.Currency;

public record CartCqrsQueryOutput(
        Long id,
        Long customerId,
        String statusName,
        String couponCode,
        BigDecimal discountAmount,
        Currency discountCurrency
) implements CqrsQueryOutput {
}
