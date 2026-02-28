package com.example.inventory.domain.warehouse.event;

import com.example.shared.domain.DomainEvent;

public sealed interface WarehouseEvent extends DomainEvent
        permits WarehouseCreatedEvent, StockReservedEvent, StockReleasedEvent {
    Long warehouseId();
}
