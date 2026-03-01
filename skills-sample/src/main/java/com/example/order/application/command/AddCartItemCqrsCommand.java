package com.example.order.application.command;

import com.example.shared.application.CqrsCommand;

import java.math.BigDecimal;
import java.util.Currency;

public record AddCartItemCqrsCommand(
        Long cartId,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        Currency currency
) implements CqrsCommand {
}
