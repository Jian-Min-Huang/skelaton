package com.example.order.domain.cart.event;

import java.time.Instant;
import java.util.UUID;

public record CartCreatedEvent(
        UUID cartId,
        UUID customerId,
        Instant occurredAt
) implements CartEvent {
}
