package com.example.inventory.application.command;

import com.example.shared.application.CqrsCommand;

public record DiscontinueProductCqrsCommand(Long productId) implements CqrsCommand {
}
