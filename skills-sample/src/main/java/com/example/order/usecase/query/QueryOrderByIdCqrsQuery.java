package com.example.order.usecase.query;

import com.example.shared.cqrs.CqrsQuery;

public record QueryOrderByIdCqrsQuery(Long orderId) implements CqrsQuery {
}
