package com.example.inventory.presentation.http.protocol;

import com.example.inventory.presentation.http.request.CreateWarehouseRequestDTO;
import com.example.inventory.presentation.http.response.WarehouseResponseDTO;
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

@Tag(name = "Warehouse", description = "Warehouse management")
public interface WarehouseProtocol extends Protocol {
    @Operation(summary = "Create a warehouse")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/v1/warehouses")
    ResponseEntity<Void> createWarehouse(@RequestBody @Validated CreateWarehouseRequestDTO request);

    @Operation(summary = "Query a warehouse by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Warehouse not found")
    })
    @GetMapping("/v1/warehouses/{warehouseId}")
    ResponseEntity<WarehouseResponseDTO> queryWarehouse(
            @Parameter(description = "Warehouse ID") @PathVariable @NotNull UUID warehouseId);
}
