package com.example.inventory.domain.product.event;

import java.time.Instant;

public record ProductDiscontinuedEvent(
        Long productId,
        Instant occurredAt
) implements ProductEvent {
}
