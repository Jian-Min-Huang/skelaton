package com.example.order.infrastructure.config;

import com.example.common.ca.EventBus;
import com.example.order.application.usecase.command.OrderCommandUseCase;
import com.example.order.application.usecase.query.OrderQueryUseCase;
import com.example.order.domain.entity.Order;
import com.example.order.domain.repository.readonly.OrderReadonlyRepository;
import com.example.order.domain.repository.writable.OrderWritableRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfiguration {
    @Bean
    OrderCommandUseCase orderCommandUseCase(EventBus eventBus, OrderWritableRepository<Order, Long> orderWritableRepository) {
        return new OrderCommandUseCase(eventBus, orderWritableRepository);
    }

    @Bean
    OrderQueryUseCase orderQueryUseCase(EventBus eventBus, OrderReadonlyRepository<Order, Long> orderReadonlyRepository) {
        return new OrderQueryUseCase(eventBus, orderReadonlyRepository);
    }
}