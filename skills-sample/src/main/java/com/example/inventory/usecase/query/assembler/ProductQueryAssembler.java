package com.example.inventory.usecase.query.assembler;

import com.example.inventory.domain.product.Product;
import com.example.inventory.domain.product.vo.Money;
import com.example.inventory.domain.product.vo.ProductSpec;
import com.example.inventory.domain.product.vo.Sku;
import com.example.inventory.usecase.query.output.ProductCqrsQueryOutput;
import com.example.shared.cqrs.CqrsQueryAssembler;
import org.springframework.stereotype.Component;

@Component
public class ProductQueryAssembler implements CqrsQueryAssembler {
    public ProductCqrsQueryOutput toOutput(final Product product) {
        final Sku sku = product.getSku();
        final Money basePrice = product.getBasePrice();
        final ProductSpec spec = product.getSpec();

        return new ProductCqrsQueryOutput(
                product.getId(),
                product.getName(),
                product.getDescription(),
                sku != null ? sku.code() : null,
                basePrice != null ? basePrice.amount() : null,
                basePrice != null ? basePrice.currency() : null,
                spec != null ? spec.brand() : null,
                spec != null ? spec.model() : null,
                spec != null ? spec.weight() : null,
                spec != null ? spec.weightUnit() : null,
                spec != null ? spec.dimensions() : null,
                product.getCategory() != null ? product.getCategory().name() : null,
                product.getStatus() != null ? product.getStatus().name() : null
        );
    }
}
