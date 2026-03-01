package com.example.inventory.presentation.http.response.converter;

import com.example.inventory.application.query.output.WarehouseCqrsQueryOutput;
import com.example.inventory.presentation.http.response.WarehouseResponseDTO;
import com.example.shared.presentation.http.ResponseConverter;
import org.springframework.stereotype.Component;

@Component
public class WarehouseResponseConverter implements ResponseConverter {
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
