package com.example.order.usecase.command;

import com.example.shared.cqrs.CqrsCommand;

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
