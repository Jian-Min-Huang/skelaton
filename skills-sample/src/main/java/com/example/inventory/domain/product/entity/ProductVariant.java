package com.example.inventory.domain.product.entity;

import com.example.inventory.domain.product.vo.Money;
import com.example.inventory.domain.product.vo.Sku;
import com.example.shared.domain.DomainEntity;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.Instant;
import java.util.UUID;

@Builder
@Value
@With
public class ProductVariant implements DomainEntity {
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
    String variantName;
    Sku sku;
    Money price;
    Integer stockQuantity;

    public ProductVariant updatePrice(final Money newPrice) {
        return this.withPrice(newPrice);
    }

    public ProductVariant updateStockQuantity(final Integer newQuantity) {
        return this.withStockQuantity(newQuantity);
    }
}
