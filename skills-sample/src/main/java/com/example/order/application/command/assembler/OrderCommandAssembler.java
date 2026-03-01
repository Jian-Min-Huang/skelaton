package com.example.order.application.command.assembler;

import com.example.order.domain.order.Order;
import com.example.order.application.command.output.OrderCqrsCommandOutput;
import com.example.shared.application.CqrsCommandAssembler;
import org.springframework.stereotype.Component;

@Component
public class OrderCommandAssembler implements CqrsCommandAssembler {
    public OrderCqrsCommandOutput toOutput(final Order order) {
        return new OrderCqrsCommandOutput(order.getId());
    }
}
