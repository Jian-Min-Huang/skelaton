package com.example.inventory.presentation.http;

import com.example.inventory.application.InventoryCommandUseCase;
import com.example.inventory.application.InventoryQueryUseCase;
import com.example.inventory.application.command.output.ProductCqrsCommandOutput;
import com.example.inventory.application.query.QueryProductByIdCqrsQuery;
import com.example.inventory.application.query.output.ProductCqrsQueryOutput;
import com.example.inventory.presentation.http.protocol.ProductProtocol;
import com.example.inventory.presentation.http.request.ActivateProductRequestDTO;
import com.example.inventory.presentation.http.request.AddProductVariantRequestDTO;
import com.example.inventory.presentation.http.request.CreateProductRequestDTO;
import com.example.inventory.presentation.http.request.DiscontinueProductRequestDTO;
import com.example.inventory.presentation.http.request.converter.ProductRequestConverter;
import com.example.inventory.presentation.http.response.ProductResponseDTO;
import com.example.inventory.presentation.http.response.converter.ProductResponseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductProtocol {
    private final InventoryCommandUseCase commandUseCase;
    private final InventoryQueryUseCase queryUseCase;
    private final ProductRequestConverter requestConverter;
    private final ProductResponseConverter responseConverter;

    @Override
    public ResponseEntity<Void> createProduct(final CreateProductRequestDTO request) {
        final ProductCqrsCommandOutput output = commandUseCase.createProduct(requestConverter.toCommand(request));
        return ResponseEntity.created(URI.create("/v1/products/" + output.productId())).build();
    }

    @Override
    public ResponseEntity<ProductResponseDTO> queryProduct(final UUID productId) {
        final ProductCqrsQueryOutput output = queryUseCase.queryProductById(new QueryProductByIdCqrsQuery(productId));
        return ResponseEntity.ok(responseConverter.toResponse(output));
    }

    @Override
    public ResponseEntity<Void> activateProduct(final ActivateProductRequestDTO request) {
        commandUseCase.activateProduct(requestConverter.toCommand(request));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> discontinueProduct(final DiscontinueProductRequestDTO request) {
        commandUseCase.discontinueProduct(requestConverter.toCommand(request));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> addProductVariant(final AddProductVariantRequestDTO request) {
        final ProductCqrsCommandOutput output = commandUseCase.addProductVariant(requestConverter.toCommand(request));
        return ResponseEntity.created(URI.create("/v1/products/" + output.productId() + "/variants")).build();
    }
}
