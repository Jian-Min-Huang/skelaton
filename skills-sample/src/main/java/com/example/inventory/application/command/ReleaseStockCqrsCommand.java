package com.example.inventory.application.command;

import com.example.shared.application.CqrsCommand;

import java.util.UUID;

public record ReleaseStockCqrsCommand(
        UUID productId,
        UUID warehouseId,
        Integer quantity
) implements CqrsCommand {
}
