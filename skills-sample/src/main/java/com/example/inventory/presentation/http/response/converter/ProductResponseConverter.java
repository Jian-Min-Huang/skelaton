package com.example.inventory.presentation.http.response.converter;

import com.example.inventory.application.query.output.ProductCqrsQueryOutput;
import com.example.inventory.presentation.http.response.ProductResponseDTO;
import com.example.shared.presentation.http.ResponseConverter;
import org.springframework.stereotype.Component;

@Component
public class ProductResponseConverter implements ResponseConverter {
    public ProductResponseDTO toResponse(final ProductCqrsQueryOutput output) {
        return new ProductResponseDTO(
                output.id(),
                output.name(),
                output.description(),
                output.skuCode(),
                output.basePrice(),
                output.currency() != null ? output.currency().getCurrencyCode() : null,
                output.brand(),
                output.model(),
                output.weight(),
                output.weightUnit(),
                output.dimensions(),
                output.categoryName(),
                output.statusName()
        );
    }
}
