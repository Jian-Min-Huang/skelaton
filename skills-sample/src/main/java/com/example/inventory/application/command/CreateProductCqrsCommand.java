package com.example.inventory.application.command;

import com.example.shared.application.CqrsCommand;

import java.math.BigDecimal;

public record CreateProductCqrsCommand(
        String name,
        String description,
        String skuCode,
        BigDecimal basePrice,
        String currency,
        String brand,
        String model,
        Double weight,
        String weightUnit,
        String dimensions,
        String category
) implements CqrsCommand {
}
