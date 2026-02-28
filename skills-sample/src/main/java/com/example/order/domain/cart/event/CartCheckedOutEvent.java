package com.example.order.domain.cart.event;

import java.time.Instant;

public record CartCheckedOutEvent(
        Long cartId,
        Instant occurredAt
) implements CartEvent {
}
