package com.example.order.application.usecase.query;

import com.example.common.ca.EventBus;
import com.example.common.ca.cqrs.CqrsInput;
import com.example.common.ca.cqrs.CqrsOutput;
import com.example.common.ca.cqrs.CqrsTemplate;
import com.example.common.data.Pagination;
import com.example.order.application.adapter.projector.OrderProjector;
import com.example.order.application.port.input.QueryOrderInput;
import com.example.order.application.port.input.QueryOrdersInput;
import com.example.order.domain.entity.Order;
import com.example.order.domain.repository.readonly.OrderReadonlyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class OrderQueryUseCase implements CqrsTemplate {
    private final EventBus eventBus;
    private final OrderReadonlyRepository<Order, Long> orderReadonlyRepository;

    @Override
    public CqrsOutput<?> execute(final CqrsInput<?> input) {
        try {
            if (input instanceof QueryOrderInput queryOrderInput) {
                final Optional<Order> entity = orderReadonlyRepository.findById(queryOrderInput.getId());

                return entity
                        .map(element -> CqrsOutput.success(OrderProjector.toOutput(element)))
                        .orElseGet(() -> CqrsOutput.failure("Order not found, ID: " + queryOrderInput.getId()));
            } else if (input instanceof QueryOrdersInput queryOrdersInput) {
                final Pagination<Order> entities;
                if (queryOrdersInput.getCustomerId() != null) {
                    entities = orderReadonlyRepository.findByCustomerId(
                            queryOrdersInput.getCustomerId(),
                            queryOrdersInput.getPage(),
                            queryOrdersInput.getSize()
                    );
                } else {
                    entities = orderReadonlyRepository.findAll(
                            queryOrdersInput.getPage(),
                            queryOrdersInput.getSize()
                    );
                }

                return CqrsOutput.success(OrderProjector.toOutput(entities));
            } else {
                return CqrsOutput.failure(OrderQueryUseCase.class.getSimpleName() + " Invalid Input: " + input.toString());
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);

            return CqrsOutput.failure(ex.getMessage());
        }
    }
}