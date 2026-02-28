package com.example.order.domain.order.event;

import java.time.Instant;

public record OrderConfirmedEvent(
        Long orderId,
        Instant occurredAt
) implements OrderEvent {
}
