package com.example.order.domain.order.repository;

import com.example.order.domain.order.Order;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order entity);
    Optional<Order> queryById(Long id);
    Integer removeById(Long id);
    Boolean existsById(Long id);
}
