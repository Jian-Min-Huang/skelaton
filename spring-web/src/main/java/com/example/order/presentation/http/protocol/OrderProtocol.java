package com.example.order.presentation.http.protocol;

import com.example.common.data.Pagination;
import com.example.order.presentation.http.request.CreateOrderRequest;
import com.example.order.presentation.http.request.ModifyOrderStatusRequest;
import com.example.order.presentation.http.request.QueryOrdersRequest;
import com.example.order.presentation.http.response.QueryOrderResponse;
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

public interface OrderProtocol {
    @Operation(
            summary = "Create a new order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @PostMapping("/api/v1/orders:create")
    ResponseEntity<Void> createOrder(@RequestBody @Validated final CreateOrderRequest request, final HttpServletRequest httpServletRequest);

    @Operation(
            summary = "Get an order by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order found"),
                    @ApiResponse(responseCode = "400", description = "Invalid order ID"),
                    @ApiResponse(responseCode = "404", description = "Order not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/api/v1/orders/{id}")
    ResponseEntity<QueryOrderResponse> queryOrderById(@Parameter(description = "order ID", required = true) @PathVariable @Positive final Long id);

    @Operation(
            summary = "Get orders by various criteria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Orders found"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Orders not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping("/api/v1/orders:queryAll")
    ResponseEntity<Pagination<QueryOrderResponse>> queryOrders(@RequestBody @Validated final QueryOrdersRequest request);

    @Operation(
            summary = "Modify an order's status",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Order status modified successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid status"),
                    @ApiResponse(responseCode = "404", description = "Order not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PatchMapping("/api/v1/orders:modifyStatus")
    ResponseEntity<Void> modifyOrderStatus(@RequestBody @Validated final ModifyOrderStatusRequest request);

    @Operation(
            summary = "Cancel an order by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Order cancelled successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid order ID"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @DeleteMapping("/api/v1/orders/{id}")
    ResponseEntity<Void> cancelOrderById(@Parameter(description = "order ID", required = true) @PathVariable @Positive final Long id);
}