package com.example.inventory.domain.warehouse;

import com.example.inventory.domain.warehouse.entity.StorageLocation;
import com.example.inventory.domain.warehouse.enu.WarehouseStatus;
import com.example.inventory.domain.warehouse.vo.Address;
import com.example.shared.domain.AggregateRoot;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.With;

import java.time.Instant;
import java.util.List;

@Builder
@Value
@With
public class Warehouse implements AggregateRoot {
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
}
