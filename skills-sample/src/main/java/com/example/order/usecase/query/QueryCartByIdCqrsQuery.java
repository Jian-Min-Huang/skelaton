package com.example.order.usecase.query;

import com.example.shared.cqrs.CqrsQuery;

public record QueryCartByIdCqrsQuery(Long cartId) implements CqrsQuery {
}
