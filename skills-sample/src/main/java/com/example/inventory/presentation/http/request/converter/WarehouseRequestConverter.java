package com.example.inventory.presentation.http.request.converter;

import com.example.inventory.application.command.CreateWarehouseCqrsCommand;
import com.example.inventory.presentation.http.request.CreateWarehouseRequestDTO;
import com.example.shared.presentation.http.RequestConverter;
import org.springframework.stereotype.Component;

@Component
public class WarehouseRequestConverter implements RequestConverter {
    public CreateWarehouseCqrsCommand toCommand(final CreateWarehouseRequestDTO request) {
        return new CreateWarehouseCqrsCommand(
                request.name(),
                request.code(),
                request.city(),
                request.district(),
                request.street(),
                request.zipCode(),
                request.capacity()
        );
    }
}
