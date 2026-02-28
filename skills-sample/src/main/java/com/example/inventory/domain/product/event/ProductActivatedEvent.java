package com.example.inventory.domain.product.event;

import java.time.Instant;

public record ProductActivatedEvent(
        Long productId,
        Instant occurredAt
) implements ProductEvent {
}
