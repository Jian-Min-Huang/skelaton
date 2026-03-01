package com.example.order.usecase.command.projector;

import com.example.order.domain.order.Order;
import com.example.order.usecase.command.output.OrderCqrsCommandOutput;
import com.example.shared.domain.CqrsCommandProjector;
import org.springframework.stereotype.Component;

@Component
public class OrderCommandProjector implements CqrsCommandProjector {
    public OrderCqrsCommandOutput toOutput(final Order order) {
        return new OrderCqrsCommandOutput(order.getId());
    }
}
