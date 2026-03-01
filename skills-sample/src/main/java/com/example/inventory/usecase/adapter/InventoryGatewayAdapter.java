package com.example.inventory.usecase.adapter;

import com.example.inventory.domain.product.Product;
import com.example.inventory.domain.product.repository.ProductRepository;
import com.example.inventory.domain.service.StockAllocationService;
import com.example.inventory.domain.warehouse.Warehouse;
import com.example.inventory.domain.warehouse.repository.WarehouseRepository;
import com.example.inventory.usecase.WarehouseFinder;
import com.example.order.port.InventoryDomainGateway;
import com.example.shared.domain.DomainResult;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class InventoryGatewayAdapter implements InventoryDomainGateway {

    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final WarehouseFinder warehouseFinder;
    private final StockAllocationService stockAllocationService;
    private final ApplicationEventPublisher eventPublisher;

    public InventoryGatewayAdapter(final ProductRepository productRepository,
                                   final WarehouseRepository warehouseRepository,
                                   final WarehouseFinder warehouseFinder,
                                   final StockAllocationService stockAllocationService,
                                   final ApplicationEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
        this.warehouseFinder = warehouseFinder;
        this.stockAllocationService = stockAllocationService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void allocateStock(final Long productId, final Integer quantity) {
        final Product product = productRepository.queryById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        final List<Warehouse> candidates = warehouseFinder.queryAll();

        final DomainResult<Warehouse> result = stockAllocationService.allocateStock(product, candidates, quantity);
        warehouseRepository.save(result.entity());
        result.events().forEach(eventPublisher::publishEvent);
    }
}
