package com.example.inventory.usecase.command;

import com.example.shared.domain.CqrsCommand;

public record ActivateProductCqrsCommand(Long productId) implements CqrsCommand {
}
