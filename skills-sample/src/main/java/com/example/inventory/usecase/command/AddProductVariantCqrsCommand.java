package com.example.inventory.usecase.command;

import com.example.shared.domain.CqrsCommand;

import java.math.BigDecimal;
import java.util.Currency;

public record AddProductVariantCqrsCommand(
        Long productId,
        String variantName,
        String skuCode,
        BigDecimal price,
        Currency currency,
        Integer stockQuantity
) implements CqrsCommand {
}
