package com.example.order.usecase.handler;

import com.example.order.domain.order.event.OrderPlacedEvent;
import com.example.order.port.InventoryDomainGateway;
import com.example.shared.domain.DomainEventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderPlacedEventHandler implements DomainEventHandler {
    private final InventoryDomainGateway inventoryPort;

    @EventListener
    public void handle(final OrderPlacedEvent event) {
        // TODO: iterate over order items to allocate stock for each product
        // For now, this demonstrates the cross-BC event handling pattern
    }
}
