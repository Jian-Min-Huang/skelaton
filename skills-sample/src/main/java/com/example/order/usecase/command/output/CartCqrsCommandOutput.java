package com.example.order.usecase.command.output;

import com.example.shared.domain.CqrsCommandOutput;

public record CartCqrsCommandOutput(Long cartId) implements CqrsCommandOutput {
}
