package com.example.order.usecase.command;

import com.example.shared.domain.CqrsCommand;

public record CreateCartCqrsCommand(Long customerId) implements CqrsCommand {
}
