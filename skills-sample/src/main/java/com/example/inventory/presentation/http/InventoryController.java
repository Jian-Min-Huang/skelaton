package com.example.inventory.presentation.http;

import com.example.inventory.application.InventoryCommandUseCase;
import com.example.inventory.presentation.http.protocol.InventoryProtocol;
import com.example.inventory.presentation.http.request.AllocateStockRequestDTO;
import com.example.inventory.presentation.http.request.ReleaseStockRequestDTO;
import com.example.inventory.presentation.http.request.converter.InventoryRequestConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InventoryController implements InventoryProtocol {
    private final InventoryCommandUseCase commandUseCase;
    private final InventoryRequestConverter requestConverter;

    @Override
    public ResponseEntity<Void> allocateStock(final AllocateStockRequestDTO request) {
        commandUseCase.allocateStock(requestConverter.toCommand(request));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> releaseStock(final ReleaseStockRequestDTO request) {
        commandUseCase.releaseStock(requestConverter.toCommand(request));
        return ResponseEntity.ok().build();
    }
}
