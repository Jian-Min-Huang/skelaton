package com.example.inventory.domain.product.event;

import com.example.shared.domain.DomainEvent;

public sealed interface ProductEvent extends DomainEvent
        permits ProductCreatedEvent, ProductActivatedEvent, ProductDiscontinuedEvent {
    Long productId();
}
