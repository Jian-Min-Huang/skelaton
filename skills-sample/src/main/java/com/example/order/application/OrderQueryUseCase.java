package com.example.order.application;

import com.example.order.domain.cart.Cart;
import com.example.order.domain.cart.repository.CartRepository;
import com.example.order.domain.order.Order;
import com.example.order.domain.order.repository.OrderRepository;
import com.example.order.application.query.QueryCartByIdCqrsQuery;
import com.example.order.application.query.QueryOrderByIdCqrsQuery;
import com.example.order.application.query.output.CartCqrsQueryOutput;
import com.example.order.application.query.output.OrderCqrsQueryOutput;
import com.example.order.application.query.assembler.CartQueryAssembler;
import com.example.order.application.query.assembler.OrderQueryAssembler;
import com.example.shared.application.CqrsQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryUseCase implements CqrsQueryUseCase {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartQueryAssembler cartQueryAssembler;
    private final OrderQueryAssembler orderQueryAssembler;

    public CartCqrsQueryOutput queryCartById(final QueryCartByIdCqrsQuery input) {
        final Cart cart = cartRepository.queryById(input.cartId())
                .orElseThrow(() -> new IllegalArgumentException("Cart not found: " + input.cartId()));
        return cartQueryAssembler.toOutput(cart);
    }

    public OrderCqrsQueryOutput queryOrderById(final QueryOrderByIdCqrsQuery input) {
        final Order order = orderRepository.queryById(input.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + input.orderId()));
        return orderQueryAssembler.toOutput(order);
    }
}
