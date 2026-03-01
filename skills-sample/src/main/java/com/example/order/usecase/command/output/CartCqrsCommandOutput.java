package com.example.order.usecase.command.output;

import com.example.shared.cqrs.CqrsCommandOutput;

public record CartCqrsCommandOutput(Long cartId) implements CqrsCommandOutput {
}
