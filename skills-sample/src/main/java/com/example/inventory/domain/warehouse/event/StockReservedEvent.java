package com.example.inventory.domain.warehouse.event;

import java.time.Instant;
import java.util.UUID;

public record StockReservedEvent(
        UUID warehouseId,
        UUID productId,
        Integer quantity,
        Instant occurredAt
) implements WarehouseEvent {
}
