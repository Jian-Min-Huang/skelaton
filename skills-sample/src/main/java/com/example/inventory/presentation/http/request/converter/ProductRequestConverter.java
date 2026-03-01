package com.example.inventory.presentation.http.request.converter;

import com.example.inventory.application.command.ActivateProductCqrsCommand;
import com.example.inventory.application.command.AddProductVariantCqrsCommand;
import com.example.inventory.application.command.CreateProductCqrsCommand;
import com.example.inventory.application.command.DiscontinueProductCqrsCommand;
import com.example.inventory.presentation.http.request.ActivateProductRequestDTO;
import com.example.inventory.presentation.http.request.AddProductVariantRequestDTO;
import com.example.inventory.presentation.http.request.CreateProductRequestDTO;
import com.example.inventory.presentation.http.request.DiscontinueProductRequestDTO;
import com.example.shared.presentation.http.RequestConverter;
import org.springframework.stereotype.Component;

@Component
public class ProductRequestConverter implements RequestConverter {
    public CreateProductCqrsCommand toCommand(final CreateProductRequestDTO request) {
        return new CreateProductCqrsCommand(
                request.getName(),
                request.getDescription(),
                request.getSkuCode(),
                request.getBasePrice(),
                request.getCurrency(),
                request.getBrand(),
                request.getModel(),
                request.getWeight(),
                request.getWeightUnit(),
                request.getDimensions(),
                request.getCategory()
        );
    }

    public ActivateProductCqrsCommand toCommand(final ActivateProductRequestDTO request) {
        return new ActivateProductCqrsCommand(request.getProductId());
    }

    public DiscontinueProductCqrsCommand toCommand(final DiscontinueProductRequestDTO request) {
        return new DiscontinueProductCqrsCommand(request.getProductId());
    }

    public AddProductVariantCqrsCommand toCommand(final AddProductVariantRequestDTO request) {
        return new AddProductVariantCqrsCommand(
                request.getProductId(),
                request.getVariantName(),
                request.getSkuCode(),
                request.getPrice(),
                request.getCurrency(),
                request.getStockQuantity()
        );
    }
}
