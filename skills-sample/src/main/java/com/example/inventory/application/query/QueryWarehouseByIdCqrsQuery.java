package com.example.inventory.application.query;

import com.example.shared.application.CqrsQuery;

public record QueryWarehouseByIdCqrsQuery(Long warehouseId) implements CqrsQuery {
}
