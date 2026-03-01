package com.example.order.presentation.http.protocol;

import com.example.order.presentation.http.response.OrderResponseDTO;
import com.example.shared.presentation.http.Protocol;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Tag(name = "Order", description = "Order management")
public interface OrderProtocol extends Protocol {
    @Operation(summary = "Query an order by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/v1/orders/{orderId}")
    ResponseEntity<OrderResponseDTO> queryOrder(
            @Parameter(description = "Order ID") @PathVariable @NotNull UUID orderId);

    @Operation(summary = "Confirm an order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order confirmed successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PostMapping("/v1/orders/{orderId}:confirm")
    ResponseEntity<Void> confirmOrder(
            @Parameter(description = "Order ID") @PathVariable @NotNull UUID orderId);

    @Operation(summary = "Ship an order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order shipped successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PostMapping("/v1/orders/{orderId}:ship")
    ResponseEntity<Void> shipOrder(
            @Parameter(description = "Order ID") @PathVariable @NotNull UUID orderId);

    @Operation(summary = "Cancel an order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PostMapping("/v1/orders/{orderId}:cancel")
    ResponseEntity<Void> cancelOrder(
            @Parameter(description = "Order ID") @PathVariable @NotNull UUID orderId);
}
