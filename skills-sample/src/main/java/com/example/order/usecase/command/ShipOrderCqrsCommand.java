package com.example.order.usecase.command;

import com.example.shared.cqrs.CqrsCommand;

public record ShipOrderCqrsCommand(Long orderId) implements CqrsCommand {
}
