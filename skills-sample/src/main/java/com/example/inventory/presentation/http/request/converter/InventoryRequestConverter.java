package com.example.inventory.presentation.http.request.converter;

import com.example.inventory.application.command.AllocateStockCqrsCommand;
import com.example.inventory.application.command.ReleaseStockCqrsCommand;
import com.example.inventory.presentation.http.request.AllocateStockRequestDTO;
import com.example.inventory.presentation.http.request.ReleaseStockRequestDTO;
import com.example.shared.presentation.http.RequestConverter;
import org.springframework.stereotype.Component;

@Component
public class InventoryRequestConverter implements RequestConverter {
    public AllocateStockCqrsCommand toCommand(final AllocateStockRequestDTO request) {
        return new AllocateStockCqrsCommand(
                request.getProductId(),
                request.getQuantity()
        );
    }

    public ReleaseStockCqrsCommand toCommand(final ReleaseStockRequestDTO request) {
        return new ReleaseStockCqrsCommand(
                request.getProductId(),
                request.getWarehouseId(),
                request.getQuantity()
        );
    }
}
