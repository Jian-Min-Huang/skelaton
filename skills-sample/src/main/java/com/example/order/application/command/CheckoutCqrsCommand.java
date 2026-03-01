package com.example.order.application.command;

import com.example.shared.application.CqrsCommand;

import java.util.UUID;

public record CheckoutCqrsCommand(
        UUID cartId,
        String orderNumber,
        String recipientName,
        String phone,
        String city,
        String district,
        String street,
        String zipCode,
        String paymentMethod
) implements CqrsCommand {
}
