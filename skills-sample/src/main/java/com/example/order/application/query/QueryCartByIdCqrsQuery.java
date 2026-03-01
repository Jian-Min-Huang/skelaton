package com.example.order.application.query;

import com.example.shared.application.CqrsQuery;

import java.util.UUID;

public record QueryCartByIdCqrsQuery(UUID cartId) implements CqrsQuery {
}
