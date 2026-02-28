package com.example.inventory.domain.warehouse.entity;

import com.example.inventory.domain.warehouse.enu.LocationType;
import com.example.inventory.domain.warehouse.vo.StockLevel;
import com.example.shared.domain.Entity;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.Instant;

@Builder
@Value
@With
public class StorageLocation implements Entity {
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
}
