package com.example.order.domain.cart.event;

import java.time.Instant;
import java.util.UUID;

public record CartCheckedOutEvent(
        UUID cartId,
        Instant occurredAt
) implements CartEvent {
}
