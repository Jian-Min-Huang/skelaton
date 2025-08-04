package com.example.order.domain.event;

import com.example.common.ca.domain.Event;
import com.example.order.domain.entity.Order;

public class QueryOrderEvent extends Event<Order> {
    private Order entity;

    @Override
    public Order extractEntity() {
        return this.entity;
    }
}