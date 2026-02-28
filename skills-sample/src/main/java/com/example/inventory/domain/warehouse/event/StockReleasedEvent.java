package com.example.inventory.domain.warehouse.event;

import java.time.Instant;

public record StockReleasedEvent(
        Long warehouseId,
        Long productId,
        Integer quantity,
        Instant occurredAt
) implements WarehouseEvent {
}
