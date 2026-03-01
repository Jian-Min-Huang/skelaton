package com.example.order.domain.cart.entity;

import com.example.order.domain.cart.vo.Money;
import com.example.shared.domain.DomainEntity;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.Instant;
import java.util.UUID;

@Builder
@Value
@With
public class CartItem implements DomainEntity {
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
    UUID productId;
    String productName;
    Integer quantity;
    Money unitPrice;

    public CartItem updateQuantity(final Integer newQuantity) {
        return this.withQuantity(newQuantity);
    }
}
