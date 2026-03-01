package com.example.inventory.usecase.query;

import com.example.shared.cqrs.CqrsQuery;

public record QueryWarehouseByIdCqrsQuery(Long warehouseId) implements CqrsQuery {
}
