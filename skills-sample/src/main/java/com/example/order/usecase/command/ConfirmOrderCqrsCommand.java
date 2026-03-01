package com.example.order.usecase.command;

import com.example.shared.cqrs.CqrsCommand;

public record ConfirmOrderCqrsCommand(Long orderId) implements CqrsCommand {
}
