package com.example.order.domain.cart.repository;

import com.example.order.domain.cart.Cart;

import java.util.Optional;

public interface CartRepository {
    Cart save(Cart entity);
    Optional<Cart> queryById(Long id);
    Integer removeById(Long id);
    Boolean existsById(Long id);
}
