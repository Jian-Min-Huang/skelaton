package com.example.inventory.application.query.output;

import com.example.shared.application.CqrsQueryOutput;

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
