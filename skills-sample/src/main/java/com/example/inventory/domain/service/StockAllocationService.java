package com.example.inventory.domain.service;

import com.example.inventory.domain.product.Product;
import com.example.inventory.domain.product.enu.ProductStatus;
import com.example.inventory.domain.warehouse.Warehouse;
import com.example.inventory.domain.warehouse.entity.StorageLocation;
import com.example.shared.domain.DomainResult;
import com.example.shared.domain.DomainService;

import java.util.List;

public class StockAllocationService implements DomainService {
    public DomainResult<Warehouse> allocateStock(final Product product,
                                                 final List<Warehouse> candidates,
                                                 final Integer quantity) {
        if (!ProductStatus.ACTIVE.equals(product.getStatus())) {
            throw new IllegalStateException("Product is not active: " + product.getId());
        }

        final Warehouse bestWarehouse = candidates.stream()
                .filter(warehouse -> warehouse.getStorageLocations().stream()
                        .anyMatch(location -> location.getProductId().equals(product.getId())
                                && location.getStockLevel().available() >= quantity))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "No warehouse has enough stock for product: " + product.getId()));

        return bestWarehouse.reserveStock(product.getId(), quantity);
    }

    public DomainResult<Warehouse> releaseStock(final Product product,
                                                final Warehouse warehouse,
                                                final Integer quantity) {
        final Boolean hasLocation = warehouse.getStorageLocations().stream()
                .anyMatch(location -> location.getProductId().equals(product.getId()));

        if (!hasLocation) {
            throw new IllegalStateException(
                    "Warehouse " + warehouse.getId() + " has no stock for product: " + product.getId());
        }

        return warehouse.releaseStock(product.getId(), quantity);
    }
}
