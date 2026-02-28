package com.example.order.domain.cart.entity;

import com.example.order.domain.cart.vo.Money;
import com.example.shared.domain.DomainResult;
import com.example.shared.domain.Entity;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.Instant;

@Builder
@Value
@With
public class CartItem implements Entity {
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
    Long productId;
    String productName;
    Integer quantity;
    Money unitPrice;

    public DomainResult<CartItem> updateQuantity(final Integer newQuantity) {
        final CartItem updated = this.withQuantity(newQuantity);
        return DomainResult.withoutEvents(updated);
    }
}
