package com.example.order.domain.cart.event;

import java.time.Instant;
import java.util.UUID;

public record CartItemAddedEvent(
        UUID cartId,
        UUID productId,
        Integer quantity,
        Instant occurredAt
) implements CartEvent {
}
