package com.example.inventory.domain.warehouse.event;

import java.time.Instant;
import java.util.UUID;

public record WarehouseCreatedEvent(
        UUID warehouseId,
        Instant occurredAt
) implements WarehouseEvent {
}
