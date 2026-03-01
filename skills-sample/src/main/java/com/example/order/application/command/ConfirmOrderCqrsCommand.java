package com.example.order.application.command;

import com.example.shared.application.CqrsCommand;

public record ConfirmOrderCqrsCommand(Long orderId) implements CqrsCommand {
}
