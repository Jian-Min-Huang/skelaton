package com.example.order.domain.order.event;

import java.time.Instant;
import java.util.UUID;

public record OrderConfirmedEvent(
        UUID orderId,
        Instant occurredAt
) implements OrderEvent {
}
