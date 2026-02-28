package com.example.order.domain.order.event;

import java.time.Instant;

public record OrderPlacedEvent(
        Long orderId,
        Long customerId,
        Instant occurredAt
) implements OrderEvent {
}
