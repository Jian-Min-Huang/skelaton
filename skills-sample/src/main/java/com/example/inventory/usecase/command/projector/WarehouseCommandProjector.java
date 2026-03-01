package com.example.inventory.usecase.command.projector;

import com.example.inventory.domain.warehouse.Warehouse;
import com.example.inventory.usecase.command.output.WarehouseCqrsCommandOutput;
import com.example.shared.domain.CqrsCommandProjector;
import org.springframework.stereotype.Component;

@Component
public class WarehouseCommandProjector implements CqrsCommandProjector {
    public WarehouseCqrsCommandOutput toOutput(final Warehouse warehouse) {
        return new WarehouseCqrsCommandOutput(warehouse.getId());
    }
}
