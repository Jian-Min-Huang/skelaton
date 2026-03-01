package com.example.inventory.domain.warehouse;

import com.example.inventory.domain.warehouse.entity.StorageLocation;
import com.example.inventory.domain.warehouse.enu.WarehouseStatus;
import com.example.inventory.domain.warehouse.event.StockReleasedEvent;
import com.example.inventory.domain.warehouse.event.StockReservedEvent;
import com.example.inventory.domain.warehouse.event.WarehouseCreatedEvent;
import com.example.inventory.domain.warehouse.vo.Address;
import com.example.shared.domain.DomainAggregateRoot;
import com.example.shared.domain.DomainResult;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.With;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
@Value
@With
public class Warehouse implements DomainAggregateRoot {
    // common fields
    UUID id;
    String createdBy;
    String updatedBy;
    String deletedBy;
    Instant createTime;
    Instant updateTime;
    Instant deleteTime;
    Boolean deleted;

    // custom fields
    String name;
    String code;
    Address address;
    Integer capacity;
    WarehouseStatus status;
    @Singular List<StorageLocation> storageLocations;

    public static DomainResult<Warehouse> create(final String name,
                                                 final String code,
                                                 final Address address,
                                                 final Integer capacity) {
        final Warehouse warehouse = Warehouse.builder()
                .id(UUID.randomUUID())
                .name(name)
                .code(code)
                .address(address)
                .capacity(capacity)
                .status(WarehouseStatus.ACTIVE)
                .deleted(false)
                .build();
        return DomainResult.of(warehouse, new WarehouseCreatedEvent(warehouse.id, Instant.now()));
    }

    public DomainResult<Warehouse> reserveStock(final UUID productId, final Integer quantity) {
        final List<StorageLocation> updatedLocations = this.storageLocations.stream()
                .map(location -> {
                    if (location.getProductId().equals(productId)) {
                        return location.withStockLevel(location.getStockLevel().reserve(quantity));
                    }
                    return location;
                })
                .toList();
        final Warehouse updatedWarehouse = this.withStorageLocations(updatedLocations);
        return DomainResult.of(updatedWarehouse,
                new StockReservedEvent(this.id, productId, quantity, Instant.now()));
    }

    public DomainResult<Warehouse> releaseStock(final UUID productId, final Integer quantity) {
        final List<StorageLocation> updatedLocations = this.storageLocations.stream()
                .map(location -> {
                    if (location.getProductId().equals(productId)) {
                        return location.withStockLevel(location.getStockLevel().release(quantity));
                    }
                    return location;
                })
                .toList();
        final Warehouse updatedWarehouse = this.withStorageLocations(updatedLocations);
        return DomainResult.of(updatedWarehouse,
                new StockReleasedEvent(this.id, productId, quantity, Instant.now()));
    }
}
