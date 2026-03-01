package com.example.order.domain.order.entity;

import com.example.order.domain.order.vo.Money;
import com.example.shared.domain.DomainEntity;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
@Value
@With
public class OrderItem implements DomainEntity {
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
    Money subtotal;

    public OrderItem updateQuantity(final Integer newQuantity) {
        final Money newSubtotal = new Money(
                this.unitPrice.amount().multiply(BigDecimal.valueOf(newQuantity)),
                this.unitPrice.currency()
        );
        return this.withQuantity(newQuantity).withSubtotal(newSubtotal);
    }
}
