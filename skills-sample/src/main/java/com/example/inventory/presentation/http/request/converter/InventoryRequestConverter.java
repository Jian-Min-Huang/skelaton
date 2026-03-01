package com.example.inventory.presentation.http.converter;

import com.example.inventory.application.command.AllocateStockCqrsCommand;
import com.example.inventory.application.command.ReleaseStockCqrsCommand;
import com.example.inventory.presentation.http.request.AllocateStockRequestDTO;
import com.example.inventory.presentation.http.request.ReleaseStockRequestDTO;
import com.example.shared.presentation.http.Converter;
import org.springframework.stereotype.Component;

@Component
public class InventoryConverter implements Converter {
    public AllocateStockCqrsCommand toCommand(final AllocateStockRequestDTO request) {
        return new AllocateStockCqrsCommand(
                request.productId(),
                request.quantity()
        );
    }

    public ReleaseStockCqrsCommand toCommand(final ReleaseStockRequestDTO request) {
        return new ReleaseStockCqrsCommand(
                request.productId(),
                request.warehouseId(),
                request.quantity()
        );
    }
}
