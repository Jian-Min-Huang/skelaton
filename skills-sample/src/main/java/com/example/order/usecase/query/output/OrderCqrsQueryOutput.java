package com.example.order.usecase.query.output;

import com.example.shared.cqrs.CqrsQueryOutput;

import java.math.BigDecimal;
import java.util.Currency;

public record OrderCqrsQueryOutput(
        Long id,
        String orderNumber,
        Long customerId,
        String recipientName,
        String phone,
        String city,
        String district,
        String street,
        String zipCode,
        BigDecimal totalAmount,
        Currency totalCurrency,
        String statusName,
        String paymentMethodName
) implements CqrsQueryOutput {
}
