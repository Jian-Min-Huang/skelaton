package com.example.inventory.presentation.http.converter;

import com.example.inventory.application.command.CreateWarehouseCqrsCommand;
import com.example.inventory.application.query.output.WarehouseCqrsQueryOutput;
import com.example.inventory.presentation.http.request.CreateWarehouseRequestDTO;
import com.example.inventory.presentation.http.response.WarehouseResponseDTO;
import com.example.shared.presentation.http.Converter;
import org.springframework.stereotype.Component;

@Component
public class WarehouseConverter implements Converter {
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

    public WarehouseResponseDTO toResponse(final WarehouseCqrsQueryOutput output) {
        return new WarehouseResponseDTO(
                output.id(),
                output.name(),
                output.code(),
                output.city(),
                output.district(),
                output.street(),
                output.zipCode(),
                output.capacity(),
                output.statusName()
        );
    }
}
