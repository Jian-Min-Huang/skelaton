package com.example.order.domain.cart.event;

import java.time.Instant;

public record CartItemAddedEvent(
        Long cartId,
        Long productId,
        Integer quantity,
        Instant occurredAt
) implements CartEvent {
}
