package com.example.order.application.command;

import com.example.shared.application.CqrsCommand;

public record CreateCartCqrsCommand(Long customerId) implements CqrsCommand {
}
