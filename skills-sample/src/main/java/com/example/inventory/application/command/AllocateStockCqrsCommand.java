package com.example.inventory.application.command;

import com.example.shared.application.CqrsCommand;

import java.util.UUID;

public record AllocateStockCqrsCommand(
        UUID productId,
        Integer quantity
) implements CqrsCommand {
}
