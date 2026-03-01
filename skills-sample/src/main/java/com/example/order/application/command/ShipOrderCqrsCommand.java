package com.example.order.application.command;

import com.example.shared.application.CqrsCommand;

public record ShipOrderCqrsCommand(Long orderId) implements CqrsCommand {
}
