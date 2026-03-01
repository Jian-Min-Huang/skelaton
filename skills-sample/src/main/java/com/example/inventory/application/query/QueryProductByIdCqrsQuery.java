package com.example.inventory.application.query;

import com.example.shared.application.CqrsQuery;

import java.util.UUID;

public record QueryProductByIdCqrsQuery(UUID productId) implements CqrsQuery {
}
