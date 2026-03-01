package com.example.inventory.domain.warehouse.vo;

import com.example.shared.domain.DomainException;
import com.example.shared.domain.DomainValueObject;

import java.util.Objects;

public record StockLevel(
        Integer onHand,
        Integer reserved,
        Integer available
) implements DomainValueObject {
    public StockLevel {
        Objects.requireNonNull(onHand, "onHand must not be null");
        Objects.requireNonNull(reserved, "reserved must not be null");
        Objects.requireNonNull(available, "available must not be null");
        if (onHand < 0) {
            throw new IllegalArgumentException("onHand must not be negative");
        }
        if (reserved < 0) {
            throw new IllegalArgumentException("reserved must not be negative");
        }
        if (available < 0) {
            throw new IllegalArgumentException("available must not be negative");
        }
    }

    public StockLevel reserve(final Integer quantity) {
        if (available < quantity) {
            throw new DomainException("Insufficient available stock: available=" + available + ", requested=" + quantity);
        }
        return new StockLevel(onHand, reserved + quantity, available - quantity);
    }

    public StockLevel release(final Integer quantity) {
        if (reserved < quantity) {
            throw new DomainException("Cannot release more than reserved: reserved=" + reserved + ", requested=" + quantity);
        }
        return new StockLevel(onHand, reserved - quantity, available + quantity);
    }
}
