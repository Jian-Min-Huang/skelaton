package com.example.inventory.domain.warehouse.vo;

import com.example.shared.domain.DomainValueObject;

public record StockLevel(
        Integer onHand,
        Integer reserved,
        Integer available
) implements DomainValueObject {
}
