package com.example.inventory.presentation.http.protocol;

import com.example.inventory.presentation.http.request.ActivateProductRequestDTO;
import com.example.inventory.presentation.http.request.AddProductVariantRequestDTO;
import com.example.inventory.presentation.http.request.CreateProductRequestDTO;
import com.example.inventory.presentation.http.request.DiscontinueProductRequestDTO;
import com.example.inventory.presentation.http.response.ProductResponseDTO;
import com.example.shared.presentation.http.Protocol;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Tag(name = "Product", description = "Product management")
public interface ProductProtocol extends Protocol {
    @Operation(summary = "Create a product")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/v1/products")
    ResponseEntity<Void> createProduct(@RequestBody @Validated CreateProductRequestDTO request);

    @Operation(summary = "Query a product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/v1/products/{productId}")
    ResponseEntity<ProductResponseDTO> queryProduct(
            @Parameter(description = "Product ID") @PathVariable @NotNull UUID productId);

    @Operation(summary = "Activate a product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PostMapping("/v1/products:activate")
    ResponseEntity<Void> activateProduct(@RequestBody @Validated ActivateProductRequestDTO request);

    @Operation(summary = "Discontinue a product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PostMapping("/v1/products:discontinue")
    ResponseEntity<Void> discontinueProduct(@RequestBody @Validated DiscontinueProductRequestDTO request);

    @Operation(summary = "Add a product variant")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PostMapping("/v1/products:add-variant")
    ResponseEntity<Void> addProductVariant(@RequestBody @Validated AddProductVariantRequestDTO request);
}
