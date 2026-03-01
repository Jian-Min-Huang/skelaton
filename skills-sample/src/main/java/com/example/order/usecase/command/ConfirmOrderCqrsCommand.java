package com.example.order.usecase.command;

import com.example.shared.domain.CqrsCommand;

public record ConfirmOrderCqrsCommand(Long orderId) implements CqrsCommand {
}
