package com.example.order.application.query.output;

import com.example.shared.application.CqrsQueryOutput;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public record CartCqrsQueryOutput(
        UUID id,
        UUID customerId,
        String statusName,
        String couponCode,
        BigDecimal discountAmount,
        Currency discountCurrency
) implements CqrsQueryOutput {
}
