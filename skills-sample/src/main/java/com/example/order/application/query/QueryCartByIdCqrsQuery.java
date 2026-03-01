package com.example.order.application.query;

import com.example.shared.application.CqrsQuery;

public record QueryCartByIdCqrsQuery(Long cartId) implements CqrsQuery {
}
