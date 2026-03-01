package com.example.inventory.application.query.output;

import com.example.shared.application.CqrsQueryOutput;

import java.math.BigDecimal;
import java.util.Currency;

public record ProductCqrsQueryOutput(
        Long id,
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
        String categoryName,
        String statusName
) implements CqrsQueryOutput {
}
