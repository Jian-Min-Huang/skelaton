package com.example.order.application.gateway;

import com.example.shared.application.Gateway;

import java.util.UUID;

public interface InventoryGateway extends Gateway {
    void allocateStock(final UUID productId, final Integer quantity);
}
