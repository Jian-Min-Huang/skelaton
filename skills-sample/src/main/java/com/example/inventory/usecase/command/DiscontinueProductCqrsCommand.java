package com.example.inventory.usecase.command;

import com.example.shared.domain.CqrsCommand;

public record DiscontinueProductCqrsCommand(Long productId) implements CqrsCommand {
}
