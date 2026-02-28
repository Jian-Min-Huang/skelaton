package com.example.inventory.domain.product.entity;

import com.example.inventory.domain.product.vo.Money;
import com.example.inventory.domain.product.vo.Sku;
import com.example.shared.domain.DomainResult;
import com.example.shared.domain.Entity;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.Instant;

@Builder
@Value
@With
public class ProductVariant implements Entity {
    // common fields
    Long id;
    String createdBy;
    String lastModifiedBy;
    String deletedBy;
    Instant createTime;
    Instant lastModifyTime;
    Instant deleteTime;
    Boolean deleted;

    // custom fields
    String variantName;
    Sku sku;
    Money price;
    Integer stockQuantity;

    public DomainResult<ProductVariant> updatePrice(final Money newPrice) {
        final ProductVariant updated = this.withPrice(newPrice);
        return DomainResult.withoutEvents(updated);
    }

    public DomainResult<ProductVariant> updateStockQuantity(final Integer newQuantity) {
        final ProductVariant updated = this.withStockQuantity(newQuantity);
        return DomainResult.withoutEvents(updated);
    }
}
