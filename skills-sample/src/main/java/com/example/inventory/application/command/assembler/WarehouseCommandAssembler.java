package com.example.inventory.application.command.assembler;

import com.example.inventory.domain.warehouse.Warehouse;
import com.example.inventory.application.command.output.WarehouseCqrsCommandOutput;
import com.example.shared.application.CqrsCommandAssembler;
import org.springframework.stereotype.Component;

@Component
public class WarehouseCommandAssembler implements CqrsCommandAssembler {
    public WarehouseCqrsCommandOutput toOutput(final Warehouse warehouse) {
        return new WarehouseCqrsCommandOutput(warehouse.getId());
    }
}
