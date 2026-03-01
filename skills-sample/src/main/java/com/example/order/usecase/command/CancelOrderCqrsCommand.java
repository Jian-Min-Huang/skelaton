package com.example.order.usecase.command;

import com.example.shared.cqrs.CqrsCommand;

public record CancelOrderCqrsCommand(Long orderId) implements CqrsCommand {
}
