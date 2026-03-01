package com.example.order.usecase.command.projector;

import com.example.order.domain.order.Order;
import com.example.order.usecase.command.output.OrderCqrsCommandOutput;
import com.example.shared.cqrs.CqrsCommandAssembler;
import org.springframework.stereotype.Component;

@Component
public class OrderCommandAssembler implements CqrsCommandAssembler {
    public OrderCqrsCommandOutput toOutput(final Order order) {
        return new OrderCqrsCommandOutput(order.getId());
    }
}
