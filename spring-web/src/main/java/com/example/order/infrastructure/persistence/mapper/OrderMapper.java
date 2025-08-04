package com.example.order.infrastructure.persistence.mapper;

import com.example.order.domain.entity.Order;
import com.example.order.domain.vo.Address;
import com.example.order.domain.vo.OrderItem;
import com.example.order.infrastructure.persistence.po.OrderPo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.List;

public class OrderMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static OrderPo toNewPo(final Order entity) {
        return OrderPo
                .builder()
                .id(entity.getId())
                .createdBy(entity.getCreatedBy())
                .lastModifiedBy(null)
                .deletedBy(null)
                .createTime(Instant.now())
                .lastModifyTime(null)
                .deleteTime(null)
                .remark(entity.getRemark())
                .deleted(0)
                .version(null)
                .orderNumber(entity.getOrderNumber())
                .customerId(entity.getCustomerId())
                .itemsJson(serializeItems(entity.getItems()))
                .totalAmount(entity.getTotalAmount())
                .currency(entity.getCurrency())
                .status(entity.getStatus())
                .paymentMethod(entity.getPaymentMethod())
                .shippingStreet(entity.getShippingAddress().getStreet())
                .shippingCity(entity.getShippingAddress().getCity())
                .shippingState(entity.getShippingAddress().getState())
                .shippingZipCode(entity.getShippingAddress().getZipCode())
                .shippingCountry(entity.getShippingAddress().getCountry())
                .billingStreet(entity.getBillingAddress().getStreet())
                .billingCity(entity.getBillingAddress().getCity())
                .billingState(entity.getBillingAddress().getState())
                .billingZipCode(entity.getBillingAddress().getZipCode())
                .billingCountry(entity.getBillingAddress().getCountry())
                .orderDate(entity.getOrderDate())
                .shippedDate(entity.getShippedDate())
                .deliveredDate(entity.getDeliveredDate())
                .build();
    }

    public static Order toEntity(final OrderPo po) {
        return Order
                .builder()
                .id(po.getId())
                .createdBy(po.getCreatedBy())
                .lastModifiedBy(po.getLastModifiedBy())
                .deletedBy(po.getDeletedBy())
                .createTime(po.getCreateTime())
                .lastModifyTime(po.getLastModifyTime())
                .deleteTime(po.getDeleteTime())
                .remark(po.getRemark())
                .deleted(po.getDeleted())
                .version(po.getVersion())
                .orderNumber(po.getOrderNumber())
                .customerId(po.getCustomerId())
                .items(deserializeItems(po.getItemsJson()))
                .totalAmount(po.getTotalAmount())
                .currency(po.getCurrency())
                .status(po.getStatus())
                .paymentMethod(po.getPaymentMethod())
                .shippingAddress(Address.fromFullAddress(
                        po.getShippingStreet(),
                        po.getShippingCity(),
                        po.getShippingState(),
                        po.getShippingZipCode(),
                        po.getShippingCountry()
                ))
                .billingAddress(Address.fromFullAddress(
                        po.getBillingStreet(),
                        po.getBillingCity(),
                        po.getBillingState(),
                        po.getBillingZipCode(),
                        po.getBillingCountry()
                ))
                .orderDate(po.getOrderDate())
                .shippedDate(po.getShippedDate())
                .deliveredDate(po.getDeliveredDate())
                .build();
    }

    private static String serializeItems(final List<OrderItem> items) {
        try {
            return objectMapper.writeValueAsString(items);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize order items", e);
        }
    }

    private static List<OrderItem> deserializeItems(final String itemsJson) {
        try {
            return objectMapper.readValue(itemsJson, new TypeReference<List<OrderItem>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize order items", e);
        }
    }
}