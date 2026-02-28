package com.example.order.domain.order.entity;

import com.example.order.domain.order.vo.Money;
import com.example.shared.domain.Entity;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.Instant;

@Builder
@Value
@With
public class OrderItem implements Entity {
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
}
