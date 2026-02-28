package com.example.order.domain.service;

import com.example.order.domain.cart.Cart;
import com.example.order.domain.order.Order;
import com.example.shared.domain.DomainResult;

public record CheckoutResult(
        DomainResult<Cart> cartResult,
        DomainResult<Order> orderResult
) {
}
