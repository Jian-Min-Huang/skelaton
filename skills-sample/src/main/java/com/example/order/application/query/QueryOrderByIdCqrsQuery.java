package com.example.order.application.query;

import com.example.shared.application.CqrsQuery;

import java.util.UUID;

public record QueryOrderByIdCqrsQuery(UUID orderId) implements CqrsQuery {
}
