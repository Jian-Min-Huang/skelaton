package com.example.inventory.domain.product.event;

import java.time.Instant;
import java.util.UUID;

public record ProductCreatedEvent(
        UUID productId,
        Instant occurredAt
) implements ProductEvent {
}
