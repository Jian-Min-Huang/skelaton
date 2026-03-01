package com.example.order.application.command.output;

import com.example.shared.application.CqrsCommandOutput;

public record OrderCqrsCommandOutput(Long orderId) implements CqrsCommandOutput {
}
