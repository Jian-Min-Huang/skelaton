package com.example.inventory.domain.warehouse.entity;

import com.example.inventory.domain.warehouse.enu.LocationType;
import com.example.inventory.domain.warehouse.vo.StockLevel;
import com.example.shared.domain.DomainEntity;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.Instant;
import java.util.UUID;

@Builder
@Value
@With
public class StorageLocation implements DomainEntity {
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
    String locationCode;
    UUID productId;
    LocationType locationType;
    StockLevel stockLevel;

    public StorageLocation updateStockLevel(final StockLevel newStockLevel) {
        return this.withStockLevel(newStockLevel);
    }

    public StorageLocation changeLocationType(final LocationType newType) {
        return this.withLocationType(newType);
    }
}
