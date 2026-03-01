package com.example.inventory.application.command.output;

import com.example.shared.application.CqrsCommandOutput;

public record ProductCqrsCommandOutput(Long productId) implements CqrsCommandOutput {
}
