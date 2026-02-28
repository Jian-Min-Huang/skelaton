package com.example.order.domain.service;

import com.example.order.domain.cart.Cart;
import com.example.order.domain.cart.enu.CartStatus;
import com.example.order.domain.order.Order;
import com.example.order.domain.order.entity.OrderItem;
import com.example.order.domain.order.enu.PaymentMethod;
import com.example.order.domain.order.vo.Money;
import com.example.order.domain.order.vo.ShippingAddress;
import com.example.shared.domain.DomainResult;
import com.example.shared.domain.DomainService;

import java.math.BigDecimal;
import java.util.List;

public class CheckoutService implements DomainService {
    public CheckoutResult checkout(final Cart cart,
                                   final String orderNumber,
                                   final ShippingAddress shippingAddress,
                                   final PaymentMethod paymentMethod) {
        if (!CartStatus.ACTIVE.equals(cart.getStatus())) {
            throw new IllegalStateException("Cart is not active: " + cart.getId());
        }

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty: " + cart.getId());
        }

        final List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> OrderItem.builder()
                        .productId(cartItem.getProductId())
                        .productName(cartItem.getProductName())
                        .quantity(cartItem.getQuantity())
                        .unitPrice(new Money(
                                cartItem.getUnitPrice().amount(),
                                cartItem.getUnitPrice().currency()))
                        .subtotal(new Money(
                                cartItem.getUnitPrice().amount()
                                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())),
                                cartItem.getUnitPrice().currency()))
                        .deleted(false)
                        .build())
                .toList();

        final Money totalAmount = orderItems.stream()
                .map(OrderItem::getSubtotal)
                .reduce((a, b) -> new Money(a.amount().add(b.amount()), a.currency()))
                .orElse(new Money(BigDecimal.ZERO, orderItems.getFirst().getSubtotal().currency()));

        final DomainResult<Cart> cartResult = cart.checkout();
        final DomainResult<Order> orderResult = Order.place(
                orderNumber, cart.getCustomerId(), shippingAddress,
                totalAmount, paymentMethod, orderItems);

        return new CheckoutResult(cartResult, orderResult);
    }
}
