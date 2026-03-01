package com.example.order.domain.order;

import com.example.order.domain.order.entity.OrderItem;
import com.example.order.domain.order.enu.OrderStatus;
import com.example.order.domain.order.enu.PaymentMethod;
import com.example.order.domain.order.event.OrderCancelledEvent;
import com.example.order.domain.order.event.OrderConfirmedEvent;
import com.example.order.domain.order.event.OrderPlacedEvent;
import com.example.order.domain.order.event.OrderShippedEvent;
import com.example.order.domain.order.vo.Money;
import com.example.order.domain.order.vo.ShippingAddress;
import com.example.shared.domain.DomainAggregateRoot;
import com.example.shared.domain.DomainException;
import com.example.shared.domain.DomainResult;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.With;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
@Value
@With
public class Order implements DomainAggregateRoot {
    // common fields
    UUID id;
    String createdBy;
    String updatedBy;
    String deletedBy;
    Instant createTime;
    Instant updateTime;
    Instant deleteTime;
    Boolean deleted;

    // custom fields
    String orderNumber;
    UUID customerId;
    ShippingAddress shippingAddress;
    Money totalAmount;
    OrderStatus status;
    PaymentMethod paymentMethod;
    @Singular List<OrderItem> items;

    public static DomainResult<Order> place(final String orderNumber,
                                            final UUID customerId,
                                            final ShippingAddress shippingAddress,
                                            final Money totalAmount,
                                            final PaymentMethod paymentMethod,
                                            final List<OrderItem> items) {
        final Order order = Order.builder()
                .id(UUID.randomUUID())
                .orderNumber(orderNumber)
                .customerId(customerId)
                .shippingAddress(shippingAddress)
                .totalAmount(totalAmount)
                .status(OrderStatus.PENDING)
                .paymentMethod(paymentMethod)
                .items(items)
                .deleted(false)
                .build();
        return DomainResult.of(order, new OrderPlacedEvent(order.id, customerId, Instant.now()));
    }

    public DomainResult<Order> confirm() {
        if (!OrderStatus.PENDING.equals(this.status)) {
            throw new DomainException("Order can only be confirmed from PENDING status, current: " + this.status);
        }
        final Order confirmed = this.withStatus(OrderStatus.CONFIRMED);
        return DomainResult.of(confirmed, new OrderConfirmedEvent(this.id, Instant.now()));
    }

    public DomainResult<Order> ship() {
        if (!OrderStatus.CONFIRMED.equals(this.status)) {
            throw new DomainException("Order can only be shipped from CONFIRMED status, current: " + this.status);
        }
        final Order shipped = this.withStatus(OrderStatus.SHIPPED);
        return DomainResult.of(shipped, new OrderShippedEvent(this.id, Instant.now()));
    }

    public DomainResult<Order> cancel() {
        if (OrderStatus.SHIPPED.equals(this.status) || OrderStatus.DELIVERED.equals(this.status)) {
            throw new DomainException("Order cannot be cancelled from " + this.status + " status");
        }
        final Order cancelled = this.withStatus(OrderStatus.CANCELLED);
        return DomainResult.of(cancelled, new OrderCancelledEvent(this.id, Instant.now()));
    }
}
