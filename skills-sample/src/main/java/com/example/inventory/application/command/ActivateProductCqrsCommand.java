package com.example.inventory.application.command;

import com.example.shared.application.CqrsCommand;

public record ActivateProductCqrsCommand(Long productId) implements CqrsCommand {
}
