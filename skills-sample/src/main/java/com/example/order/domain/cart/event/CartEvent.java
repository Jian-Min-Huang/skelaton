package com.example.order.domain.cart.event;

import com.example.shared.domain.DomainEvent;

public sealed interface CartEvent extends DomainEvent
        permits CartCreatedEvent, CartItemAddedEvent, CartCheckedOutEvent {
    Long cartId();
}
