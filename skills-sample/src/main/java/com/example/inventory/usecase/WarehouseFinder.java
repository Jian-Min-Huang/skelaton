package com.example.inventory.usecase;

import com.example.inventory.domain.warehouse.Warehouse;
import com.example.shared.domain.DomainFinder;

import java.util.List;

public interface WarehouseFinder extends DomainFinder {
    List<Warehouse> queryAll();
}
