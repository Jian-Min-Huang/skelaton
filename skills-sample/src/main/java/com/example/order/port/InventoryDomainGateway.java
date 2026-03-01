package com.example.order.port;

import com.example.shared.domain.DomainGateway;

public interface InventoryDomainGateway extends DomainGateway {
    void allocateStock(final Long productId, final Integer quantity);
}
