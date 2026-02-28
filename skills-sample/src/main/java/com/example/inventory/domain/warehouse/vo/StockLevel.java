package com.example.inventory.domain.warehouse.vo;

import com.example.shared.domain.ValueObject;

public record StockLevel(
        Integer onHand,
        Integer reserved,
        Integer available
) implements ValueObject {
}
