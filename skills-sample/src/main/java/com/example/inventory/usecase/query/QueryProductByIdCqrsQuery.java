package com.example.inventory.usecase.query;

import com.example.shared.cqrs.CqrsQuery;

public record QueryProductByIdCqrsQuery(Long productId) implements CqrsQuery {
}
