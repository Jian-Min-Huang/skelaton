package com.example.inventory.application.command;

import com.example.shared.application.CqrsCommand;

public record AllocateStockCqrsCommand(
        Long productId,
        Integer quantity
) implements CqrsCommand {
}
