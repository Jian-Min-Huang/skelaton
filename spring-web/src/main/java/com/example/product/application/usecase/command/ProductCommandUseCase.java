package com.example.product.application.usecase.command;

import com.example.common.ca.EventBus;
import com.example.common.ca.cqrs.CqrsInput;
import com.example.common.ca.cqrs.CqrsOutput;
import com.example.common.ca.cqrs.CqrsTemplate;
import com.example.product.application.adapter.projector.ProductProjector;
import com.example.product.application.port.input.CreateProductInput;
import com.example.product.application.port.input.ModifyProductPriceInput;
import com.example.product.application.port.input.RemoveProductInput;
import com.example.product.domain.entity.Product;
import com.example.product.domain.repository.writable.ProductWritableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ProductCommandUseCase implements CqrsTemplate {
    private final EventBus eventBus;
    private final ProductWritableRepository<Product, Long> productWritableRepository;

    @Override
    public CqrsOutput<?> execute(final CqrsInput<?> input) {
        try {
            switch (input) {
                case CreateProductInput createProductInput -> {
                    final Product entity = productWritableRepository.save(ProductProjector.toEntity(createProductInput));
                    return CqrsOutput.success(entity.getId());
                }
                case ModifyProductPriceInput modifyProductPriceInput -> {
                    productWritableRepository.modifyPrice(ProductProjector.toEntity(modifyProductPriceInput));
                    return CqrsOutput.success(modifyProductPriceInput.getId());
                }
                case RemoveProductInput removeProductInput -> {
                    productWritableRepository.remove(removeProductInput.getId());
                    return CqrsOutput.success(removeProductInput.getId());
                }
                default -> {
                    return CqrsOutput.failure(ProductCommandUseCase.class.getSimpleName() + " Invalid Input: " + input);
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);

            return CqrsOutput.failure(ex.getMessage());
        }
    }
}