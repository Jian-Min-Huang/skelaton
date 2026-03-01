package com.example.order.usecase;

import com.example.order.domain.cart.Cart;
import com.example.order.domain.cart.repository.CartRepository;
import com.example.order.domain.order.Order;
import com.example.order.domain.order.repository.OrderRepository;
import com.example.order.usecase.query.QueryCartByIdCqrsQuery;
import com.example.order.usecase.query.QueryOrderByIdCqrsQuery;
import com.example.order.usecase.query.output.CartCqrsQueryOutput;
import com.example.order.usecase.query.output.OrderCqrsQueryOutput;
import com.example.order.usecase.query.projector.CartQueryProjector;
import com.example.order.usecase.query.projector.OrderQueryProjector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryUseCase {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartQueryProjector cartQueryProjector;
    private final OrderQueryProjector orderQueryProjector;

    public CartCqrsQueryOutput queryCartById(final QueryCartByIdCqrsQuery input) {
        final Cart cart = cartRepository.queryById(input.cartId())
                .orElseThrow(() -> new IllegalArgumentException("Cart not found: " + input.cartId()));
        return cartQueryProjector.toOutput(cart);
    }

    public OrderCqrsQueryOutput queryOrderById(final QueryOrderByIdCqrsQuery input) {
        final Order order = orderRepository.queryById(input.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + input.orderId()));
        return orderQueryProjector.toOutput(order);
    }
}
