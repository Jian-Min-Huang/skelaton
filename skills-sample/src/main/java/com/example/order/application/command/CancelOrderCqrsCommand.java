package com.example.order.application.command;

import com.example.shared.application.CqrsCommand;

public record CancelOrderCqrsCommand(Long orderId) implements CqrsCommand {
}
