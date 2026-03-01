package com.example.inventory.usecase.query;

import com.example.shared.domain.CqrsQuery;

public record QueryWarehouseByIdCqrsQuery(Long warehouseId) implements CqrsQuery {
}
