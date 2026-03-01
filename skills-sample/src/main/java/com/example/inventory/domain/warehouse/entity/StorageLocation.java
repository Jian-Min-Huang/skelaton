package com.example.inventory.domain.warehouse.entity;

import com.example.inventory.domain.warehouse.enu.LocationType;
import com.example.inventory.domain.warehouse.vo.StockLevel;
import com.example.shared.domain.DomainResult;
import com.example.shared.domain.DomainEntity;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.Instant;

@Builder
@Value
@With
public class StorageLocation implements DomainEntity {
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
    String locationCode;
    Long productId;
    LocationType locationType;
    StockLevel stockLevel;

    public DomainResult<StorageLocation> updateStockLevel(final StockLevel newStockLevel) {
        final StorageLocation updated = this.withStockLevel(newStockLevel);
        return DomainResult.withoutEvents(updated);
    }

    public DomainResult<StorageLocation> changeLocationType(final LocationType newType) {
        final StorageLocation updated = this.withLocationType(newType);
        return DomainResult.withoutEvents(updated);
    }
}
