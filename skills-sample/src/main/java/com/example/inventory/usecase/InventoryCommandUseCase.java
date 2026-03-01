package com.example.inventory.usecase;

import com.example.inventory.domain.product.Product;
import com.example.inventory.domain.product.entity.ProductVariant;
import com.example.inventory.domain.product.repository.ProductRepository;
import com.example.inventory.domain.product.vo.Money;
import com.example.inventory.domain.product.vo.ProductSpec;
import com.example.inventory.domain.product.vo.Sku;
import com.example.inventory.domain.service.StockAllocationService;
import com.example.inventory.domain.warehouse.Warehouse;
import com.example.inventory.domain.warehouse.repository.WarehouseRepository;
import com.example.inventory.domain.warehouse.vo.Address;
import com.example.inventory.usecase.command.ActivateProductCqrsCommand;
import com.example.inventory.usecase.command.AddProductVariantCqrsCommand;
import com.example.inventory.usecase.command.CreateProductCqrsCommand;
import com.example.inventory.usecase.command.CreateWarehouseCqrsCommand;
import com.example.inventory.usecase.command.DiscontinueProductCqrsCommand;
import com.example.inventory.usecase.command.output.ProductCqrsCommandOutput;
import com.example.inventory.usecase.command.output.WarehouseCqrsCommandOutput;
import com.example.inventory.usecase.command.assembler.ProductCommandAssembler;
import com.example.inventory.usecase.command.assembler.WarehouseCommandAssembler;
import com.example.shared.cqrs.CqrsCommandUseCase;
import com.example.shared.domain.DomainResult;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryCommandUseCase implements CqrsCommandUseCase {
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final StockAllocationService stockAllocationService;
    private final ApplicationEventPublisher eventPublisher;
    private final ProductCommandAssembler productCommandAssembler;
    private final WarehouseCommandAssembler warehouseCommandAssembler;

    public ProductCqrsCommandOutput createProduct(final CreateProductCqrsCommand command) {
        final Sku sku = new Sku(command.skuCode());
        final Money basePrice = new Money(command.basePrice(), command.currency());
        final ProductSpec spec = new ProductSpec(
                command.brand(),
                command.model(),
                command.weight(),
                command.weightUnit(),
                command.dimensions()
        );
        final DomainResult<Product> result = Product.create(
                command.name(),
                command.description(),
                sku,
                basePrice,
                spec,
                command.category()
        );
        final Product saved = productRepository.save(result.entity());
        publishEvents(result);
        return productCommandAssembler.toOutput(saved);
    }

    public ProductCqrsCommandOutput activateProduct(final ActivateProductCqrsCommand command) {
        final Product product = productRepository.queryById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + command.productId()));
        final DomainResult<Product> result = product.activate();
        final Product saved = productRepository.save(result.entity());
        publishEvents(result);
        return productCommandAssembler.toOutput(saved);
    }

    public ProductCqrsCommandOutput discontinueProduct(final DiscontinueProductCqrsCommand command) {
        final Product product = productRepository.queryById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + command.productId()));
        final DomainResult<Product> result = product.discontinue();
        final Product saved = productRepository.save(result.entity());
        publishEvents(result);
        return productCommandAssembler.toOutput(saved);
    }

    public ProductCqrsCommandOutput addProductVariant(final AddProductVariantCqrsCommand command) {
        final Product product = productRepository.queryById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + command.productId()));
        final Sku sku = new Sku(command.skuCode());
        final Money price = new Money(command.price(), command.currency());
        final ProductVariant variant = ProductVariant.builder()
                .variantName(command.variantName())
                .sku(sku)
                .price(price)
                .stockQuantity(command.stockQuantity())
                .deleted(false)
                .build();
        final DomainResult<Product> result = product.addVariant(variant);
        final Product saved = productRepository.save(result.entity());
        publishEvents(result);
        return productCommandAssembler.toOutput(saved);
    }

    public WarehouseCqrsCommandOutput createWarehouse(final CreateWarehouseCqrsCommand command) {
        final Address address = new Address(
                command.city(),
                command.district(),
                command.street(),
                command.zipCode()
        );
        final DomainResult<Warehouse> result = Warehouse.create(
                command.name(),
                command.code(),
                address,
                command.capacity()
        );
        final Warehouse saved = warehouseRepository.save(result.entity());
        publishEvents(result);
        return warehouseCommandAssembler.toOutput(saved);
    }

    private void publishEvents(final DomainResult<?> result) {
        result.events().forEach(eventPublisher::publishEvent);
    }
}
