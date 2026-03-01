package com.example.inventory.domain.product.event;

import java.time.Instant;
import java.util.UUID;

public record ProductActivatedEvent(
        UUID productId,
        Instant occurredAt
) implements ProductEvent {
}
