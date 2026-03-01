package com.example.inventory.application.command;

import com.example.inventory.domain.product.enu.Category;
import com.example.shared.application.CqrsCommand;

import java.math.BigDecimal;
import java.util.Currency;

public record CreateProductCqrsCommand(
        String name,
        String description,
        String skuCode,
        BigDecimal basePrice,
        Currency currency,
        String brand,
        String model,
        Double weight,
        String weightUnit,
        String dimensions,
        Category category
) implements CqrsCommand {
}
