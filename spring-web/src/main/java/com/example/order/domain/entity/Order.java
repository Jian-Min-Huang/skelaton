package com.example.order.domain.entity;

import com.example.common.ca.domain.Entity;
import com.example.order.domain.vo.Address;
import com.example.order.domain.vo.OrderItem;
import com.example.order.domain.vo.enu.OrderStatus;
import com.example.order.domain.vo.enu.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Order extends Entity<Long> {
    private String orderNumber;
    private Long customerId;
    private List<OrderItem> items;
    private BigDecimal totalAmount;
    private String currency;
    private OrderStatus status;
    private PaymentMethod paymentMethod;
    private Address shippingAddress;
    private Address billingAddress;
    private Instant orderDate;
    private Instant shippedDate;
    private Instant deliveredDate;
}