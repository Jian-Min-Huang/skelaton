package com.example.inventory.presentation.http.request.converter;

import com.example.inventory.application.command.ActivateProductCqrsCommand;
import com.example.inventory.application.command.AddProductVariantCqrsCommand;
import com.example.inventory.application.command.CreateProductCqrsCommand;
import com.example.inventory.application.command.DiscontinueProductCqrsCommand;
import com.example.inventory.domain.product.enu.Category;
import com.example.inventory.presentation.http.request.ActivateProductRequestDTO;
import com.example.inventory.presentation.http.request.AddProductVariantRequestDTO;
import com.example.inventory.presentation.http.request.CreateProductRequestDTO;
import com.example.inventory.presentation.http.request.DiscontinueProductRequestDTO;
import com.example.shared.presentation.http.RequestConverter;
import org.springframework.stereotype.Component;

import java.util.Currency;

@Component
public class ProductRequestConverter implements RequestConverter {
    public CreateProductCqrsCommand toCommand(final CreateProductRequestDTO request) {
        return new CreateProductCqrsCommand(
                request.name(),
                request.description(),
                request.skuCode(),
                request.basePrice(),
                Currency.getInstance(request.currency()),
                request.brand(),
                request.model(),
                request.weight(),
                request.weightUnit(),
                request.dimensions(),
                Category.valueOf(request.category())
        );
    }

    public ActivateProductCqrsCommand toCommand(final ActivateProductRequestDTO request) {
        return new ActivateProductCqrsCommand(request.productId());
    }

    public DiscontinueProductCqrsCommand toCommand(final DiscontinueProductRequestDTO request) {
        return new DiscontinueProductCqrsCommand(request.productId());
    }

    public AddProductVariantCqrsCommand toCommand(final AddProductVariantRequestDTO request) {
        return new AddProductVariantCqrsCommand(
                request.productId(),
                request.variantName(),
                request.skuCode(),
                request.price(),
                Currency.getInstance(request.currency()),
                request.stockQuantity()
        );
    }
}
