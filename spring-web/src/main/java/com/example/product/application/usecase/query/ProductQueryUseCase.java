package com.example.product.application.usecase.query;

import com.example.common.ca.EventBus;
import com.example.common.ca.cqrs.CqrsInput;
import com.example.common.ca.cqrs.CqrsOutput;
import com.example.common.ca.cqrs.CqrsTemplate;
import com.example.common.data.Pagination;
import com.example.product.application.adapter.projector.ProductProjector;
import com.example.product.application.port.input.QueryProductInput;
import com.example.product.application.port.input.QueryProductsInput;
import com.example.product.domain.entity.Product;
import com.example.product.domain.repository.readonly.ProductReadonlyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class ProductQueryUseCase implements CqrsTemplate {
    private final EventBus eventBus;
    private final ProductReadonlyRepository<Product, Long> productReadonlyRepository;

    @Override
    public CqrsOutput<?> execute(final CqrsInput<?> input) {
        try {
            if (input instanceof QueryProductInput queryProductInput) {
                final Optional<Product> entity = productReadonlyRepository.findById(queryProductInput.getId());

                return entity
                        .map(element -> CqrsOutput.success(ProductProjector.toOutput(element)))
                        .orElseGet(() -> CqrsOutput.failure("Product not found, ID: " + queryProductInput.getId()));
            } else if (input instanceof QueryProductsInput queryProductsInput) {
                final Pagination<Product> entities = productReadonlyRepository.findAll(
                        queryProductsInput.getPage(),
                        queryProductsInput.getSize()
                );

                return CqrsOutput.success(ProductProjector.toOutput(entities));
            } else {
                return CqrsOutput.failure(ProductQueryUseCase.class.getSimpleName() + " Invalid Input: " + input.toString());
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);

            return CqrsOutput.failure(ex.getMessage());
        }
    }
}