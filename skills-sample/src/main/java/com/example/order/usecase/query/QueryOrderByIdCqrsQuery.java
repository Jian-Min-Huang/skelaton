package com.example.order.usecase.query;

import com.example.shared.domain.CqrsQuery;

public record QueryOrderByIdCqrsQuery(Long orderId) implements CqrsQuery {
}
