package com.example.inventory.application.command;

import com.example.shared.application.CqrsCommand;

import java.math.BigDecimal;
import java.util.UUID;

public record AddProductVariantCqrsCommand(
        UUID productId,
        String variantName,
        String skuCode,
        BigDecimal price,
        String currency,
        Integer stockQuantity
) implements CqrsCommand {
}
