package com.example.order.domain.order.entity;

import com.example.order.domain.order.vo.Money;
import com.example.shared.domain.DomainResult;
import com.example.shared.domain.DomainEntity;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Value
@With
public class OrderItem implements DomainEntity {
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
    Money subtotal;

    public DomainResult<OrderItem> updateQuantity(final Integer newQuantity) {
        final Money newSubtotal = new Money(
                this.unitPrice.amount().multiply(BigDecimal.valueOf(newQuantity)),
                this.unitPrice.currency()
        );
        final OrderItem updated = this.withQuantity(newQuantity).withSubtotal(newSubtotal);
        return DomainResult.withoutEvents(updated);
    }
}
