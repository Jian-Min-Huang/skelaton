package com.example.inventory.domain.product.event;

import java.time.Instant;

public record ProductCreatedEvent(
        Long productId,
        Instant occurredAt
) implements ProductEvent {
}
