package com.example.inventory.usecase.query;

import com.example.shared.domain.CqrsQuery;

public record QueryProductByIdCqrsQuery(Long productId) implements CqrsQuery {
}
