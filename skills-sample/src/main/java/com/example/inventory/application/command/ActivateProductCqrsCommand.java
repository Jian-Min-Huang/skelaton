package com.example.inventory.application.command;

import com.example.shared.application.CqrsCommand;

import java.util.UUID;

public record ActivateProductCqrsCommand(UUID productId) implements CqrsCommand {
}
