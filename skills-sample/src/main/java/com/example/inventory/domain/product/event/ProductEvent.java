package com.example.inventory.domain.product.event;

import com.example.shared.domain.DomainEvent;

import java.util.UUID;

public sealed interface ProductEvent extends DomainEvent
        permits ProductCreatedEvent, ProductActivatedEvent, ProductDiscontinuedEvent {
    UUID productId();
}
