package com.example.order.application.query.output;

import com.example.shared.application.CqrsQueryOutput;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public record OrderCqrsQueryOutput(
        UUID id,
        String orderNumber,
        UUID customerId,
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
