package com.example.inventory.application.command.assembler;

import com.example.inventory.domain.product.Product;
import com.example.inventory.application.command.output.ProductCqrsCommandOutput;
import com.example.shared.application.CqrsCommandAssembler;
import org.springframework.stereotype.Component;

@Component
public class ProductCommandAssembler implements CqrsCommandAssembler {
    public ProductCqrsCommandOutput toOutput(final Product product) {
        return new ProductCqrsCommandOutput(product.getId());
    }
}
