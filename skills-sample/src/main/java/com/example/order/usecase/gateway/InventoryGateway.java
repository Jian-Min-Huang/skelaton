package com.example.order.usecase.gateway;

import com.example.shared.Gateway;

public interface InventoryGateway extends Gateway {
    void allocateStock(final Long productId, final Integer quantity);
}
