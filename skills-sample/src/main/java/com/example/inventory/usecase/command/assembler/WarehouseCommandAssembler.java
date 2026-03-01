package com.example.inventory.usecase.command.assembler;

import com.example.inventory.domain.warehouse.Warehouse;
import com.example.inventory.usecase.command.output.WarehouseCqrsCommandOutput;
import com.example.shared.cqrs.CqrsCommandAssembler;
import org.springframework.stereotype.Component;

@Component
public class WarehouseCommandAssembler implements CqrsCommandAssembler {
    public WarehouseCqrsCommandOutput toOutput(final Warehouse warehouse) {
        return new WarehouseCqrsCommandOutput(warehouse.getId());
    }
}
