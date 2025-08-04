package com.example.product.infrastructure.config;

import com.example.common.ca.EventBus;
import com.example.product.application.usecase.command.ProductCommandUseCase;
import com.example.product.application.usecase.query.ProductQueryUseCase;
import com.example.product.domain.entity.Product;
import com.example.product.domain.repository.readonly.ProductReadonlyRepository;
import com.example.product.domain.repository.writable.ProductWritableRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfiguration {
    @Bean
    ProductCommandUseCase productCommandUseCase(EventBus eventBus, ProductWritableRepository<Product, Long> productWritableRepository) {
        return new ProductCommandUseCase(eventBus, productWritableRepository);
    }

    @Bean
    ProductQueryUseCase productQueryUseCase(EventBus eventBus, ProductReadonlyRepository<Product, Long> productReadonlyRepository) {
        return new ProductQueryUseCase(eventBus, productReadonlyRepository);
    }
}