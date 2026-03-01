package com.example.inventory.application.command;

import com.example.shared.application.CqrsCommand;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public record AddProductVariantCqrsCommand(
        UUID productId,
        String variantName,
        String skuCode,
        BigDecimal price,
        Currency currency,
        Integer stockQuantity
) implements CqrsCommand {
}
