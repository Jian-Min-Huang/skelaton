package com.example.order.usecase.command;

import com.example.shared.cqrs.CqrsCommand;

public record CreateCartCqrsCommand(Long customerId) implements CqrsCommand {
}
