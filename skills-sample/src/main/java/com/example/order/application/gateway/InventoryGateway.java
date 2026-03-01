package com.example.order.application.gateway;

import com.example.shared.application.Gateway;

public interface InventoryGateway extends Gateway {
    void allocateStock(final Long productId, final Integer quantity);
}
