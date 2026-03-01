package com.example.order.usecase.handler;

import com.example.order.domain.order.event.OrderPlacedEvent;
import com.example.order.port.InventoryDomainGateway;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderPlacedEventHandler {

    private final InventoryDomainGateway inventoryPort;

    public OrderPlacedEventHandler(final InventoryDomainGateway inventoryPort) {
        this.inventoryPort = inventoryPort;
    }

    @EventListener
    public void handle(final OrderPlacedEvent event) {
        // TODO: iterate over order items to allocate stock for each product
        // For now, this demonstrates the cross-BC event handling pattern
    }
}
