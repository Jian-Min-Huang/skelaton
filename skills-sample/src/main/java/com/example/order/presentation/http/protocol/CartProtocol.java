package com.example.order.presentation.http.protocol;

import com.example.order.presentation.http.request.AddCartItemRequestDTO;
import com.example.order.presentation.http.request.CheckoutRequestDTO;
import com.example.order.presentation.http.request.CreateCartRequestDTO;
import com.example.order.presentation.http.response.CartResponseDTO;
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

@Tag(name = "Cart", description = "Shopping cart management")
public interface CartProtocol extends Protocol {
    @Operation(summary = "Create a cart")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/v1/carts")
    ResponseEntity<Void> createCart(@RequestBody @Validated CreateCartRequestDTO request);

    @Operation(summary = "Query a cart by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @GetMapping("/v1/carts/{cartId}")
    ResponseEntity<CartResponseDTO> queryCart(
            @Parameter(description = "Cart ID") @PathVariable @NotNull UUID cartId);

    @Operation(summary = "Add an item to a cart")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @PostMapping("/v1/carts/{cartId}:add-item")
    ResponseEntity<Void> addCartItem(
            @Parameter(description = "Cart ID") @PathVariable @NotNull UUID cartId,
            @RequestBody @Validated AddCartItemRequestDTO request);

    @Operation(summary = "Checkout a cart to create an order")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @PostMapping("/v1/carts/{cartId}:checkout")
    ResponseEntity<Void> checkout(
            @Parameter(description = "Cart ID") @PathVariable @NotNull UUID cartId,
            @RequestBody @Validated CheckoutRequestDTO request);
}
