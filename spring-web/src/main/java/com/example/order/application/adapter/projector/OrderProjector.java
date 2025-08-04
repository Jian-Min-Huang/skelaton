package com.example.order.application.adapter.projector;

import com.example.common.data.Pagination;
import com.example.order.application.adapter.vo.AddressVoModel;
import com.example.order.application.adapter.vo.OrderItemVoModel;
import com.example.order.application.adapter.vo.enu.OrderStatusEnuModel;
import com.example.order.application.adapter.vo.enu.PaymentMethodEnuModel;
import com.example.order.application.port.input.CreateOrderInput;
import com.example.order.application.port.input.ModifyOrderStatusInput;
import com.example.order.application.port.output.QueryOrderOutputData;
import com.example.order.domain.entity.Order;
import com.example.order.domain.vo.Address;
import com.example.order.domain.vo.OrderItem;
import com.example.order.domain.vo.enu.OrderStatus;
import com.example.order.domain.vo.enu.PaymentMethod;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class OrderProjector {
    public static Order toEntity(final CreateOrderInput input) {
        final BigDecimal totalAmount = input.getItems().stream()
                .map(OrderItemVoModel::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return Order
                .builder()
                .id(null)
                .createdBy(null)
                .lastModifiedBy(null)
                .deletedBy(null)
                .createTime(null)
                .lastModifyTime(null)
                .deleteTime(null)
                .remark(null)
                .deleted(0)
                .orderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .customerId(input.getCustomerId())
                .items(input.getItems().stream().map(OrderProjector::toOrderItem).toList())
                .totalAmount(totalAmount)
                .currency(input.getCurrency())
                .status(OrderStatus.PENDING)
                .paymentMethod(PaymentMethod.valueOf(input.getPaymentMethod().name()))
                .shippingAddress(toAddress(input.getShippingAddress()))
                .billingAddress(toAddress(input.getBillingAddress()))
                .orderDate(Instant.now())
                .shippedDate(null)
                .deliveredDate(null)
                .build();
    }

    public static Order toEntity(final ModifyOrderStatusInput input) {
        return Order
                .builder()
                .id(input.getId())
                .status(OrderStatus.valueOf(input.getStatus().name()))
                .build();
    }

    public static QueryOrderOutputData toOutput(final Order entity) {
        return QueryOrderOutputData
                .builder()
                .id(entity.getId())
                .createTime(entity.getCreateTime())
                .orderNumber(entity.getOrderNumber())
                .customerId(entity.getCustomerId())
                .items(entity.getItems().stream().map(OrderProjector::toOrderItemVoModel).toList())
                .totalAmount(entity.getTotalAmount())
                .currency(entity.getCurrency())
                .status(OrderStatusEnuModel.valueOf(entity.getStatus().name()))
                .paymentMethod(PaymentMethodEnuModel.valueOf(entity.getPaymentMethod().name()))
                .shippingAddress(toAddressVoModel(entity.getShippingAddress()))
                .billingAddress(toAddressVoModel(entity.getBillingAddress()))
                .orderDate(entity.getOrderDate())
                .shippedDate(entity.getShippedDate())
                .deliveredDate(entity.getDeliveredDate())
                .build();
    }

    public static Pagination<QueryOrderOutputData> toOutput(final Pagination<Order> entities) {
        return Pagination
                .<QueryOrderOutputData>builder()
                .content(entities.getContent().stream().map(OrderProjector::toOutput).toList())
                .currentPage(entities.getCurrentPage())
                .pageSize(entities.getPageSize())
                .totalPages(entities.getTotalPages())
                .totalElements(entities.getTotalElements())
                .build();
    }

    private static OrderItem toOrderItem(final OrderItemVoModel model) {
        return OrderItem.builder()
                .productId(model.getProductId())
                .productName(model.getProductName())
                .quantity(model.getQuantity())
                .unitPrice(model.getUnitPrice())
                .totalPrice(model.getTotalPrice())
                .build();
    }

    private static Address toAddress(final AddressVoModel model) {
        return Address.builder()
                .street(model.getStreet())
                .city(model.getCity())
                .state(model.getState())
                .zipCode(model.getZipCode())
                .country(model.getCountry())
                .build();
    }

    private static OrderItemVoModel toOrderItemVoModel(final OrderItem item) {
        return OrderItemVoModel.builder()
                .productId(item.getProductId())
                .productName(item.getProductName())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .totalPrice(item.getTotalPrice())
                .build();
    }

    private static AddressVoModel toAddressVoModel(final Address address) {
        return AddressVoModel.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .country(address.getCountry())
                .build();
    }
}