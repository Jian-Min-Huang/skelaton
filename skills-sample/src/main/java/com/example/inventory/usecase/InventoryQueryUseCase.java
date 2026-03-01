package com.example.inventory.usecase;

import com.example.inventory.domain.product.Product;
import com.example.inventory.domain.product.repository.ProductRepository;
import com.example.inventory.domain.warehouse.Warehouse;
import com.example.inventory.domain.warehouse.repository.WarehouseRepository;
import com.example.inventory.usecase.query.QueryProductByIdCqrsQuery;
import com.example.inventory.usecase.query.QueryWarehouseByIdCqrsQuery;
import com.example.inventory.usecase.query.output.ProductCqrsQueryOutput;
import com.example.inventory.usecase.query.output.WarehouseCqrsQueryOutput;
import com.example.inventory.usecase.query.projector.ProductQueryAssembler;
import com.example.inventory.usecase.query.projector.WarehouseQueryAssembler;
import com.example.shared.cqrs.CqrsQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryQueryUseCase implements CqrsQueryUseCase {
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductQueryAssembler productQueryProjector;
    private final WarehouseQueryAssembler warehouseQueryProjector;

    public ProductCqrsQueryOutput queryProductById(final QueryProductByIdCqrsQuery input) {
        final Product product = productRepository.queryById(input.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + input.productId()));
        return productQueryProjector.toOutput(product);
    }

    public WarehouseCqrsQueryOutput queryWarehouseById(final QueryWarehouseByIdCqrsQuery input) {
        final Warehouse warehouse = warehouseRepository.queryById(input.warehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found: " + input.warehouseId()));
        return warehouseQueryProjector.toOutput(warehouse);
    }
}
