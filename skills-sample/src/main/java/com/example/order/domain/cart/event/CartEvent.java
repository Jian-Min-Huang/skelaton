package com.example.order.domain.cart.event;

import com.example.shared.domain.DomainEvent;

import java.util.UUID;

public sealed interface CartEvent extends DomainEvent
        permits CartCreatedEvent, CartItemAddedEvent, CartCheckedOutEvent {
    UUID cartId();
}
