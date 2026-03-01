package com.example.order.application.command.output;

import com.example.shared.application.CqrsCommandOutput;

public record CartCqrsCommandOutput(Long cartId) implements CqrsCommandOutput {
}
