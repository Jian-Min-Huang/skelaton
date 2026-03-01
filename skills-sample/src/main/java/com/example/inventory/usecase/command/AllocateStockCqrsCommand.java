package com.example.inventory.usecase.command;

import com.example.shared.domain.CqrsCommand;

public record AllocateStockCqrsCommand(
        Long productId,
        Integer quantity
) implements CqrsCommand {
}
