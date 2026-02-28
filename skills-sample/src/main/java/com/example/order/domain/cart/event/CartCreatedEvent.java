package com.example.order.domain.cart.event;

import java.time.Instant;

public record CartCreatedEvent(
        Long cartId,
        Long customerId,
        Instant occurredAt
) implements CartEvent {
}
