package com.example.inventory.usecase;

import com.example.inventory.domain.warehouse.Warehouse;
import com.example.shared.Finder;

import java.util.List;

public interface WarehouseFinder extends Finder {
    List<Warehouse> queryAll();
}
