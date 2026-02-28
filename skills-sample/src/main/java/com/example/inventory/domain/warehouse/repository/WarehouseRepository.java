package com.example.inventory.domain.warehouse.repository;

import com.example.inventory.domain.warehouse.Warehouse;

import java.util.Optional;

public interface WarehouseRepository {
    Warehouse save(Warehouse entity);
    Optional<Warehouse> queryById(Long id);
    Integer removeById(Long id);
    Boolean existsById(Long id);
}
