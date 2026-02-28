package com.example.order.domain.order;

import com.example.order.domain.order.entity.OrderItem;
import com.example.order.domain.order.enu.OrderStatus;
import com.example.order.domain.order.enu.PaymentMethod;
import com.example.order.domain.order.vo.ShippingAddress;
import com.example.order.domain.order.vo.Money;
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
public class Order implements AggregateRoot {
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
    String orderNumber;
    Long customerId;
    ShippingAddress shippingAddress;
    Money totalAmount;
    OrderStatus status;
    PaymentMethod paymentMethod;
    @Singular List<OrderItem> items;
}
