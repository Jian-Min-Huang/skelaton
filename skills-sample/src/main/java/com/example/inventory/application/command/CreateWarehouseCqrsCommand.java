package com.example.inventory.application.command;

import com.example.shared.application.CqrsCommand;

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
