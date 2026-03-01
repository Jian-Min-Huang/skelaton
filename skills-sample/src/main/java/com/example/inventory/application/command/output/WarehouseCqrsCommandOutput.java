package com.example.inventory.application.command.output;

import com.example.shared.application.CqrsCommandOutput;

import java.util.UUID;

public record WarehouseCqrsCommandOutput(UUID warehouseId) implements CqrsCommandOutput {
}
