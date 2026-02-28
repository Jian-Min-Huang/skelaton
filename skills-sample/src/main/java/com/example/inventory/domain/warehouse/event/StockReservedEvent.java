package com.example.inventory.domain.warehouse.event;

import java.time.Instant;

public record StockReservedEvent(
        Long warehouseId,
        Long productId,
        Integer quantity,
        Instant occurredAt
) implements WarehouseEvent {
}
