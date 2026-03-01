package com.example.order.usecase.query.projector;

import com.example.order.domain.order.Order;
import com.example.order.domain.order.vo.Money;
import com.example.order.domain.order.vo.ShippingAddress;
import com.example.order.usecase.query.output.OrderCqrsQueryOutput;
import com.example.shared.domain.CqrsQueryProjector;
import org.springframework.stereotype.Component;

@Component
public class OrderQueryProjector implements CqrsQueryProjector {
    public OrderCqrsQueryOutput toOutput(final Order order) {
        final ShippingAddress shippingAddress = order.getShippingAddress();
        final Money totalAmount = order.getTotalAmount();
        return new OrderCqrsQueryOutput(
                order.getId(),
                order.getOrderNumber(),
                order.getCustomerId(),
                shippingAddress != null ? shippingAddress.recipientName() : null,
                shippingAddress != null ? shippingAddress.phone() : null,
                shippingAddress != null ? shippingAddress.city() : null,
                shippingAddress != null ? shippingAddress.district() : null,
                shippingAddress != null ? shippingAddress.street() : null,
                shippingAddress != null ? shippingAddress.zipCode() : null,
                totalAmount != null ? totalAmount.amount() : null,
                totalAmount != null ? totalAmount.currency() : null,
                order.getStatus().name(),
                order.getPaymentMethod().name()
        );
    }
}
