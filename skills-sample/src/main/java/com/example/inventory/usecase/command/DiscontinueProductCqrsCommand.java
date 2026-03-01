package com.example.inventory.usecase.command;

import com.example.shared.cqrs.CqrsCommand;

public record DiscontinueProductCqrsCommand(Long productId) implements CqrsCommand {
}
