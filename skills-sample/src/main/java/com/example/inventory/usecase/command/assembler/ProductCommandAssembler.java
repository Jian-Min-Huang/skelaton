package com.example.inventory.usecase.command.assembler;

import com.example.inventory.domain.product.Product;
import com.example.inventory.usecase.command.output.ProductCqrsCommandOutput;
import com.example.shared.cqrs.CqrsCommandAssembler;
import org.springframework.stereotype.Component;

@Component
public class ProductCommandAssembler implements CqrsCommandAssembler {
    public ProductCqrsCommandOutput toOutput(final Product product) {
        return new ProductCqrsCommandOutput(product.getId());
    }
}
