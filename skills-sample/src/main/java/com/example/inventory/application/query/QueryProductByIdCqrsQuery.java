package com.example.inventory.application.query;

import com.example.shared.application.CqrsQuery;

public record QueryProductByIdCqrsQuery(Long productId) implements CqrsQuery {
}
