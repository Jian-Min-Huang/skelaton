package com.example.inventory.usecase;

import com.example.inventory.domain.warehouse.Warehouse;

import java.util.List;

public interface WarehouseQueryRepository {
    List<Warehouse> queryAll();
}
