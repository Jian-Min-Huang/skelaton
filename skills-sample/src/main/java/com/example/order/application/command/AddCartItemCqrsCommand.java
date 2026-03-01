package com.example.order.application.command;

import com.example.shared.application.CqrsCommand;

import java.math.BigDecimal;
import java.util.UUID;

public record AddCartItemCqrsCommand(
        UUID cartId,
        UUID productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        String currency
) implements CqrsCommand {
}
