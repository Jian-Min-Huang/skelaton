package com.example.order.domain.event;

import com.example.common.ca.domain.Event;
import com.example.order.domain.entity.Order;

public class ModifyOrderEvent extends Event<Order> {
    private Order entity;

    @Override
    public Order extractEntity() {
        return this.entity;
    }
}