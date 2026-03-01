package com.example.inventory.usecase.command;

import com.example.shared.cqrs.CqrsCommand;

public record ActivateProductCqrsCommand(Long productId) implements CqrsCommand {
}
