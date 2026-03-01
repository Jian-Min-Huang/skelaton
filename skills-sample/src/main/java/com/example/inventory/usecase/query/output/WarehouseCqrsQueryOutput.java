package com.example.inventory.usecase.query.output;

import com.example.shared.domain.CqrsQueryOutput;

public record WarehouseCqrsQueryOutput(
        Long id,
        String name,
        String code,
        String city,
        String district,
        String street,
        String zipCode,
        Integer capacity,
        String statusName
) implements CqrsQueryOutput {
}
