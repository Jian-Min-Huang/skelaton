package com.example.order.usecase.handler;

import com.example.order.domain.order.event.OrderPlacedEvent;
import com.example.order.usecase.gateway.InventoryGateway;
import com.example.shared.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderPlacedEventHandler implements EventHandler {
    private final InventoryGateway inventoryPort;

    @EventListener
    public void handle(final OrderPlacedEvent event) {
        // TODO: iterate over order items to allocate stock for each product
        // For now, this demonstrates the cross-BC event handling pattern
    }
}
