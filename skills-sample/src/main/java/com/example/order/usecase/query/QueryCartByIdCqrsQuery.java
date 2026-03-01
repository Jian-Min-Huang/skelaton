package com.example.order.usecase.query;

import com.example.shared.domain.CqrsQuery;

public record QueryCartByIdCqrsQuery(Long cartId) implements CqrsQuery {
}
