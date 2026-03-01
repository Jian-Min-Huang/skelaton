package com.example.inventory.usecase.command.output;

import com.example.shared.domain.CqrsCommandOutput;

public record WarehouseCqrsCommandOutput(Long warehouseId) implements CqrsCommandOutput {
}
