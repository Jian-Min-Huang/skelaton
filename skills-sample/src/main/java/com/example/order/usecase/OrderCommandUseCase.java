package com.example.order.usecase;

import com.example.order.domain.cart.Cart;
import com.example.order.domain.cart.repository.CartRepository;
import com.example.order.domain.cart.vo.Money;
import com.example.order.domain.order.Order;
import com.example.order.domain.order.repository.OrderRepository;
import com.example.order.domain.order.vo.ShippingAddress;
import com.example.order.domain.service.CheckoutResult;
import com.example.order.domain.service.CheckoutService;
import com.example.order.usecase.command.AddCartItemCqrsCommand;
import com.example.order.usecase.command.CancelOrderCqrsCommand;
import com.example.order.usecase.command.CheckoutCqrsCommand;
import com.example.order.usecase.command.ConfirmOrderCqrsCommand;
import com.example.order.usecase.command.CreateCartCqrsCommand;
import com.example.order.usecase.command.ShipOrderCqrsCommand;
import com.example.order.usecase.command.output.CartCqrsCommandOutput;
import com.example.order.usecase.command.output.OrderCqrsCommandOutput;
import com.example.order.usecase.command.projector.CartCommandProjector;
import com.example.order.usecase.command.projector.OrderCommandProjector;
import com.example.shared.domain.CqrsCommandUseCase;
import com.example.shared.domain.DomainResult;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderCommandUseCase implements CqrsCommandUseCase {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CheckoutService checkoutService;
    private final ApplicationEventPublisher eventPublisher;
    private final CartCommandProjector cartCommandProjector;
    private final OrderCommandProjector orderCommandProjector;

    public CartCqrsCommandOutput createCart(final CreateCartCqrsCommand command) {
        final DomainResult<Cart> result = Cart.create(command.customerId());
        final Cart saved = cartRepository.save(result.entity());
        publishEvents(result);
        return cartCommandProjector.toOutput(saved);
    }

    public CartCqrsCommandOutput addCartItem(final AddCartItemCqrsCommand command) {
        final Cart cart = cartRepository.queryById(command.cartId())
                .orElseThrow(() -> new IllegalArgumentException("Cart not found: " + command.cartId()));
        final Money unitPrice = new Money(command.unitPrice(), command.currency());
        final DomainResult<Cart> result = cart.addItem(
                command.productId(),
                command.productName(),
                command.quantity(),
                unitPrice);
        final Cart saved = cartRepository.save(result.entity());
        publishEvents(result);
        return cartCommandProjector.toOutput(saved);
    }

    public OrderCqrsCommandOutput checkout(final CheckoutCqrsCommand command) {
        final Cart cart = cartRepository.queryById(command.cartId())
                .orElseThrow(() -> new IllegalArgumentException("Cart not found: " + command.cartId()));
        final ShippingAddress shippingAddress = new ShippingAddress(
                command.recipientName(),
                command.phone(),
                command.city(),
                command.district(),
                command.street(),
                command.zipCode());
        final CheckoutResult checkoutResult = checkoutService.checkout(
                cart,
                command.orderNumber(),
                shippingAddress,
                command.paymentMethod());
        final DomainResult<Cart> cartResult = checkoutResult.cartResult();
        final DomainResult<Order> orderResult = checkoutResult.orderResult();
        cartRepository.save(cartResult.entity());
        final Order savedOrder = orderRepository.save(orderResult.entity());
        publishEvents(cartResult);
        publishEvents(orderResult);
        return orderCommandProjector.toOutput(savedOrder);
    }

    public OrderCqrsCommandOutput confirmOrder(final ConfirmOrderCqrsCommand command) {
        final Order order = orderRepository.queryById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + command.orderId()));
        final DomainResult<Order> result = order.confirm();
        final Order saved = orderRepository.save(result.entity());
        publishEvents(result);
        return orderCommandProjector.toOutput(saved);
    }

    public OrderCqrsCommandOutput shipOrder(final ShipOrderCqrsCommand command) {
        final Order order = orderRepository.queryById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + command.orderId()));
        final DomainResult<Order> result = order.ship();
        final Order saved = orderRepository.save(result.entity());
        publishEvents(result);
        return orderCommandProjector.toOutput(saved);
    }

    public OrderCqrsCommandOutput cancelOrder(final CancelOrderCqrsCommand command) {
        final Order order = orderRepository.queryById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + command.orderId()));
        final DomainResult<Order> result = order.cancel();
        final Order saved = orderRepository.save(result.entity());
        publishEvents(result);
        return orderCommandProjector.toOutput(saved);
    }

    private void publishEvents(final DomainResult<?> result) {
        result.events().forEach(eventPublisher::publishEvent);
    }
}
