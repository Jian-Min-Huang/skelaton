package com.example.inventory.domain.warehouse.event;

import java.time.Instant;

public record WarehouseCreatedEvent(
        Long warehouseId,
        Instant occurredAt
) implements WarehouseEvent {
}
