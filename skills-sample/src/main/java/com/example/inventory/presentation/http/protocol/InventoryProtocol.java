package com.example.inventory.presentation.http.protocol;

import com.example.inventory.presentation.http.request.AllocateStockRequestDTO;
import com.example.inventory.presentation.http.request.ReleaseStockRequestDTO;
import com.example.shared.presentation.http.Protocol;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Inventory", description = "Inventory stock operations")
public interface InventoryProtocol extends Protocol {
    @Operation(summary = "Allocate stock for a product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock allocated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PostMapping("/v1/inventory:allocate-stock")
    ResponseEntity<Void> allocateStock(@RequestBody @Validated AllocateStockRequestDTO request);

    @Operation(summary = "Release reserved stock for a product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock released successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Product or warehouse not found")
    })
    @PostMapping("/v1/inventory:release-stock")
    ResponseEntity<Void> releaseStock(@RequestBody @Validated ReleaseStockRequestDTO request);
}
