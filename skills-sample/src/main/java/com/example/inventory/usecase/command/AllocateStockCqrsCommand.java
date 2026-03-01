package com.example.inventory.usecase.command;

import com.example.shared.cqrs.CqrsCommand;

public record AllocateStockCqrsCommand(
        Long productId,
        Integer quantity
) implements CqrsCommand {
}
