package com.example.inventory.application;

import com.example.inventory.domain.warehouse.Warehouse;
import com.example.shared.application.Finder;

import java.util.List;

public interface WarehouseFinder extends Finder {
    List<Warehouse> queryAll();
}
