package com.example.order.domain.order.event;

import java.time.Instant;
import java.util.UUID;

public record OrderPlacedEvent(
        UUID orderId,
        UUID customerId,
        Instant occurredAt
) implements OrderEvent {
}
