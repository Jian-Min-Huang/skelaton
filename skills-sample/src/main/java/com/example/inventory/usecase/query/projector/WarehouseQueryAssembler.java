package com.example.inventory.usecase.query.projector;

import com.example.inventory.domain.warehouse.Warehouse;
import com.example.inventory.domain.warehouse.vo.Address;
import com.example.inventory.usecase.query.output.WarehouseCqrsQueryOutput;
import com.example.shared.cqrs.CqrsQueryAssembler;
import org.springframework.stereotype.Component;

@Component
public class WarehouseQueryAssembler implements CqrsQueryAssembler {
    public WarehouseCqrsQueryOutput toOutput(final Warehouse warehouse) {
        final Address address = warehouse.getAddress();

        return new WarehouseCqrsQueryOutput(
                warehouse.getId(),
                warehouse.getName(),
                warehouse.getCode(),
                address != null ? address.city() : null,
                address != null ? address.district() : null,
                address != null ? address.street() : null,
                address != null ? address.zipCode() : null,
                warehouse.getCapacity(),
                warehouse.getStatus() != null ? warehouse.getStatus().name() : null
        );
    }
}
