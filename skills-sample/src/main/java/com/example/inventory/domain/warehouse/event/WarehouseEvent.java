package com.example.inventory.domain.warehouse.event;

import com.example.shared.domain.DomainEvent;

import java.util.UUID;

public sealed interface WarehouseEvent extends DomainEvent
        permits WarehouseCreatedEvent, StockReservedEvent, StockReleasedEvent {
    UUID warehouseId();
}
