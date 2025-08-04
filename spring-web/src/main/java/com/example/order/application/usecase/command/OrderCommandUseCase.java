package com.example.order.application.usecase.command;

import com.example.common.ca.EventBus;
import com.example.common.ca.cqrs.CqrsInput;
import com.example.common.ca.cqrs.CqrsOutput;
import com.example.common.ca.cqrs.CqrsTemplate;
import com.example.order.application.adapter.projector.OrderProjector;
import com.example.order.application.port.input.CancelOrderInput;
import com.example.order.application.port.input.CreateOrderInput;
import com.example.order.application.port.input.ModifyOrderStatusInput;
import com.example.order.domain.entity.Order;
import com.example.order.domain.repository.writable.OrderWritableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class OrderCommandUseCase implements CqrsTemplate {
    private final EventBus eventBus;
    private final OrderWritableRepository<Order, Long> orderWritableRepository;

    @Override
    public CqrsOutput<?> execute(final CqrsInput<?> input) {
        try {
            switch (input) {
                case CreateOrderInput createOrderInput -> {
                    final Order entity = orderWritableRepository.save(OrderProjector.toEntity(createOrderInput));
                    return CqrsOutput.success(entity.getId());
                }
                case ModifyOrderStatusInput modifyOrderStatusInput -> {
                    orderWritableRepository.modifyStatus(OrderProjector.toEntity(modifyOrderStatusInput));
                    return CqrsOutput.success(modifyOrderStatusInput.getId());
                }
                case CancelOrderInput cancelOrderInput -> {
                    orderWritableRepository.cancel(cancelOrderInput.getId());
                    return CqrsOutput.success(cancelOrderInput.getId());
                }
                default -> {
                    return CqrsOutput.failure(OrderCommandUseCase.class.getSimpleName() + " Invalid Input: " + input);
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);

            return CqrsOutput.failure(ex.getMessage());
        }
    }
}