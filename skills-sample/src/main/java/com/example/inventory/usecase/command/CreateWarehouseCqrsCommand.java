package com.example.inventory.usecase.command;

import com.example.shared.cqrs.CqrsCommand;

public record CreateWarehouseCqrsCommand(
        String name,
        String code,
        String city,
        String district,
        String street,
        String zipCode,
        Integer capacity
) implements CqrsCommand {
}
