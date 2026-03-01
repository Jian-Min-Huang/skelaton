package com.example.inventory.presentation.http;

import com.example.inventory.application.InventoryCommandUseCase;
import com.example.inventory.application.InventoryQueryUseCase;
import com.example.inventory.application.command.output.WarehouseCqrsCommandOutput;
import com.example.inventory.application.query.QueryWarehouseByIdCqrsQuery;
import com.example.inventory.application.query.output.WarehouseCqrsQueryOutput;
import com.example.inventory.presentation.http.protocol.WarehouseProtocol;
import com.example.inventory.presentation.http.request.CreateWarehouseRequestDTO;
import com.example.inventory.presentation.http.request.converter.WarehouseRequestConverter;
import com.example.inventory.presentation.http.response.WarehouseResponseDTO;
import com.example.inventory.presentation.http.response.converter.WarehouseResponseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class WarehouseController implements WarehouseProtocol {
    private final InventoryCommandUseCase commandUseCase;
    private final InventoryQueryUseCase queryUseCase;
    private final WarehouseRequestConverter requestConverter;
    private final WarehouseResponseConverter responseConverter;

    @Override
    public ResponseEntity<Void> createWarehouse(final CreateWarehouseRequestDTO request) {
        final WarehouseCqrsCommandOutput output = commandUseCase.createWarehouse(requestConverter.toCommand(request));
        return ResponseEntity.created(URI.create("/v1/warehouses/" + output.warehouseId())).build();
    }

    @Override
    public ResponseEntity<WarehouseResponseDTO> queryWarehouse(final UUID warehouseId) {
        final WarehouseCqrsQueryOutput output = queryUseCase.queryWarehouseById(new QueryWarehouseByIdCqrsQuery(warehouseId));
        return ResponseEntity.ok(responseConverter.toResponse(output));
    }
}
