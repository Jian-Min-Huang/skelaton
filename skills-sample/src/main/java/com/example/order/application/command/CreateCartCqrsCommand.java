package com.example.order.application.command;

import com.example.shared.application.CqrsCommand;

import java.util.UUID;

public record CreateCartCqrsCommand(UUID customerId) implements CqrsCommand {
}
