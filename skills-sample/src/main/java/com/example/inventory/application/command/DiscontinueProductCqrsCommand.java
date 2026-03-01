package com.example.inventory.application.command;

import com.example.shared.application.CqrsCommand;

import java.util.UUID;

public record DiscontinueProductCqrsCommand(UUID productId) implements CqrsCommand {
}
