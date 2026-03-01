package com.example.order.usecase.command;

import com.example.order.domain.order.enu.PaymentMethod;
import com.example.shared.domain.CqrsCommand;

public record CheckoutCqrsCommand(
        Long cartId,
        String orderNumber,
        String recipientName,
        String phone,
        String city,
        String district,
        String street,
        String zipCode,
        PaymentMethod paymentMethod
) implements CqrsCommand {
}
