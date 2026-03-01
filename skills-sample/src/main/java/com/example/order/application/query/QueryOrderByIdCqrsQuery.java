package com.example.order.application.query;

import com.example.shared.application.CqrsQuery;

public record QueryOrderByIdCqrsQuery(Long orderId) implements CqrsQuery {
}
