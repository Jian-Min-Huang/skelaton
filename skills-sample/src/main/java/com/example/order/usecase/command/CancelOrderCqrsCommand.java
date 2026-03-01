package com.example.order.usecase.command;

import com.example.shared.domain.CqrsCommand;

public record CancelOrderCqrsCommand(Long orderId) implements CqrsCommand {
}
