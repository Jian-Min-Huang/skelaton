package com.example.product.presentation.http.protocol;

import com.example.common.data.Pagination;
import com.example.product.presentation.http.request.CreateProductRequest;
import com.example.product.presentation.http.request.ModifyProductPriceRequest;
import com.example.product.presentation.http.request.QueryProductsRequest;
import com.example.product.presentation.http.response.QueryProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProductProtocol {
    @Operation(
            summary = "Create a new product",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Product created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @PostMapping("/api/v1/products:create")
    ResponseEntity<Void> createProduct(@RequestBody @Validated final CreateProductRequest request, final HttpServletRequest httpServletRequest);

    @Operation(
            summary = "Get a product by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product found"),
                    @ApiResponse(responseCode = "400", description = "Invalid product ID"),
                    @ApiResponse(responseCode = "404", description = "Product not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/api/v1/products/{id}")
    ResponseEntity<QueryProductResponse> queryProductById(@Parameter(description = "product ID", required = true) @PathVariable @Positive final Long id);

    @Operation(
            summary = "Get products by various criteria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Products found"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Products not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping("/api/v1/products:queryAll")
    ResponseEntity<Pagination<QueryProductResponse>> queryProducts(@RequestBody @Validated final QueryProductsRequest request);

    @Operation(
            summary = "Modify a product's price",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Product price modified successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid price format"),
                    @ApiResponse(responseCode = "404", description = "Product not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PatchMapping("/api/v1/products:modifyPrice")
    ResponseEntity<Void> modifyProductPrice(@RequestBody @Validated final ModifyProductPriceRequest request);

    @Operation(
            summary = "Delete a product by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid product ID"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @DeleteMapping("/api/v1/products/{id}")
    ResponseEntity<Void> removeProductById(@Parameter(description = "product ID", required = true) @PathVariable @Positive final Long id);
}