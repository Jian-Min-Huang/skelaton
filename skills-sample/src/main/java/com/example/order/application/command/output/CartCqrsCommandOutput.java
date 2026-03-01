package com.example.order.application.command.output;

import com.example.shared.application.CqrsCommandOutput;

import java.util.UUID;

public record CartCqrsCommandOutput(UUID cartId) implements CqrsCommandOutput {
}
