package com.example.order.domain.cart;

import com.example.order.domain.cart.entity.CartItem;
import com.example.order.domain.cart.enu.CartStatus;
import com.example.order.domain.cart.vo.CartDiscount;
import com.example.shared.domain.AggregateRoot;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.With;

import java.time.Instant;
import java.util.List;

@Builder
@Value
@With
public class Cart implements AggregateRoot {
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
    Long customerId;
    CartStatus status;
    CartDiscount discount;
    @Singular List<CartItem> items;
}
