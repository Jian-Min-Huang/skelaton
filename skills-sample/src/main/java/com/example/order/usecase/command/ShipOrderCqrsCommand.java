package com.example.order.usecase.command;

import com.example.shared.domain.CqrsCommand;

public record ShipOrderCqrsCommand(Long orderId) implements CqrsCommand {
}
