package com.example.inventory.domain.warehouse;

import com.example.inventory.domain.warehouse.entity.StorageLocation;
import com.example.inventory.domain.warehouse.enu.WarehouseStatus;
import com.example.inventory.domain.warehouse.event.StockReleasedEvent;
import com.example.inventory.domain.warehouse.event.StockReservedEvent;
import com.example.inventory.domain.warehouse.event.WarehouseCreatedEvent;
import com.example.inventory.domain.warehouse.vo.Address;
import com.example.inventory.domain.warehouse.vo.StockLevel;
import com.example.shared.domain.DomainAggRoot;
import com.example.shared.domain.DomainResult;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.With;

import java.time.Instant;
import java.util.List;

@Builder
@Value
@With
public class Warehouse implements DomainAggRoot {
    // common fields
    Long id;
    String createdBy;
    String lastModifiedBy;
    String deletedBy;
    Instant createTime;
    Instant lastModifyTime;
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
                .name(name)
                .code(code)
                .address(address)
                .capacity(capacity)
                .status(WarehouseStatus.ACTIVE)
                .deleted(false)
                .build();
        return DomainResult.of(warehouse, new WarehouseCreatedEvent(warehouse.id, Instant.now()));
    }

    public DomainResult<Warehouse> reserveStock(final Long productId, final Integer quantity) {
        final List<StorageLocation> updatedLocations = this.storageLocations.stream()
                .map(location -> {
                    if (location.getProductId().equals(productId)) {
                        final StockLevel current = location.getStockLevel();
                        final StockLevel updated = new StockLevel(
                                current.onHand(),
                                current.reserved() + quantity,
                                current.available() - quantity
                        );
                        return location.withStockLevel(updated);
                    }
                    return location;
                })
                .toList();
        final Warehouse updatedWarehouse = this.withStorageLocations(updatedLocations);
        return DomainResult.of(updatedWarehouse,
                new StockReservedEvent(this.id, productId, quantity, Instant.now()));
    }

    public DomainResult<Warehouse> releaseStock(final Long productId, final Integer quantity) {
        final List<StorageLocation> updatedLocations = this.storageLocations.stream()
                .map(location -> {
                    if (location.getProductId().equals(productId)) {
                        final StockLevel current = location.getStockLevel();
                        final StockLevel updated = new StockLevel(
                                current.onHand(),
                                current.reserved() - quantity,
                                current.available() + quantity
                        );
                        return location.withStockLevel(updated);
                    }
                    return location;
                })
                .toList();
        final Warehouse updatedWarehouse = this.withStorageLocations(updatedLocations);
        return DomainResult.of(updatedWarehouse,
                new StockReleasedEvent(this.id, productId, quantity, Instant.now()));
    }
}
