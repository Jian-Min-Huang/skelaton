package com.example.inventory.usecase.command.output;

import com.example.shared.cqrs.CqrsCommandOutput;

public record ProductCqrsCommandOutput(Long productId) implements CqrsCommandOutput {
}
