package com.example.inventory.domain.product;

import com.example.inventory.domain.product.entity.ProductVariant;
import com.example.inventory.domain.product.enu.Category;
import com.example.inventory.domain.product.enu.ProductStatus;
import com.example.inventory.domain.product.event.ProductActivatedEvent;
import com.example.inventory.domain.product.event.ProductCreatedEvent;
import com.example.inventory.domain.product.event.ProductDiscontinuedEvent;
import com.example.inventory.domain.product.vo.Money;
import com.example.inventory.domain.product.vo.ProductSpec;
import com.example.inventory.domain.product.vo.Sku;
import com.example.shared.domain.DomainAggregateRoot;
import com.example.shared.domain.DomainException;
import com.example.shared.domain.DomainResult;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.With;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Value
@With
public class Product implements DomainAggregateRoot {
    // common fields
    UUID id;
    String createdBy;
    String updatedBy;
    String deletedBy;
    Instant createTime;
    Instant updateTime;
    Instant deleteTime;
    Boolean deleted;

    // custom fields
    String name;
    String description;
    Sku sku;
    Money basePrice;
    ProductSpec spec;
    Category category;
    ProductStatus status;
    @Singular List<ProductVariant> variants;

    public static DomainResult<Product> create(final String name,
                                               final String description,
                                               final Sku sku,
                                               final Money basePrice,
                                               final ProductSpec spec,
                                               final Category category) {
        final Product product = Product.builder()
                .id(UUID.randomUUID())
                .name(name)
                .description(description)
                .sku(sku)
                .basePrice(basePrice)
                .spec(spec)
                .category(category)
                .status(ProductStatus.DRAFT)
                .deleted(false)
                .build();
        return DomainResult.of(product, new ProductCreatedEvent(product.id, Instant.now()));
    }

    public DomainResult<Product> activate() {
        if (!ProductStatus.DRAFT.equals(this.status)) {
            throw new DomainException("Product can only be activated from DRAFT status, current: " + this.status);
        }
        final Product activated = this.withStatus(ProductStatus.ACTIVE);
        return DomainResult.of(activated, new ProductActivatedEvent(this.id, Instant.now()));
    }

    public DomainResult<Product> discontinue() {
        if (!ProductStatus.ACTIVE.equals(this.status)) {
            throw new DomainException("Product can only be discontinued from ACTIVE status, current: " + this.status);
        }
        final Product discontinued = this.withStatus(ProductStatus.DISCONTINUED);
        return DomainResult.of(discontinued, new ProductDiscontinuedEvent(this.id, Instant.now()));
    }

    public DomainResult<Product> addVariant(final ProductVariant variant) {
        final List<ProductVariant> newVariants = new ArrayList<>(this.variants);
        newVariants.add(variant);
        final Product updated = this.withVariants(newVariants);
        return DomainResult.withoutEvents(updated);
    }

    public DomainResult<Product> removeVariant(final UUID variantId) {
        final List<ProductVariant> newVariants = this.variants.stream()
                .filter(v -> !v.getId().equals(variantId))
                .toList();
        final Product updated = this.withVariants(newVariants);
        return DomainResult.withoutEvents(updated);
    }
}
