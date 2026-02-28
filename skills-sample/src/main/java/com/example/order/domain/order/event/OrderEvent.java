package com.example.order.domain.order.event;

import com.example.shared.domain.DomainEvent;

public sealed interface OrderEvent extends DomainEvent
        permits OrderPlacedEvent, OrderConfirmedEvent, OrderShippedEvent, OrderCancelledEvent {
    Long orderId();
}
