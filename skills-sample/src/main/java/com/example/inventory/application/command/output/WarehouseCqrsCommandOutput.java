package com.example.inventory.application.command.output;

import com.example.shared.application.CqrsCommandOutput;

public record WarehouseCqrsCommandOutput(Long warehouseId) implements CqrsCommandOutput {
}
