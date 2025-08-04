package com.example.order.presentation.http.converter;

import com.example.order.application.adapter.vo.AddressVoModel;
import com.example.order.application.adapter.vo.OrderItemVoModel;
import com.example.order.application.adapter.vo.enu.OrderStatusEnuModel;
import com.example.order.application.adapter.vo.enu.PaymentMethodEnuModel;
import com.example.order.application.port.input.CancelOrderInput;
import com.example.order.application.port.input.CreateOrderInput;
import com.example.order.application.port.input.ModifyOrderStatusInput;
import com.example.order.application.port.input.QueryOrderInput;
import com.example.order.application.port.input.QueryOrdersInput;
import com.example.order.application.port.output.QueryOrderOutputData;
import com.example.order.presentation.http.dto.AddressDto;
import com.example.order.presentation.http.dto.OrderItemDto;
import com.example.order.presentation.http.dto.enu.OrderStatusEnuDto;
import com.example.order.presentation.http.dto.enu.PaymentMethodEnuDto;
import com.example.order.presentation.http.request.CreateOrderRequest;
import com.example.order.presentation.http.request.ModifyOrderStatusRequest;
import com.example.order.presentation.http.request.QueryOrdersRequest;
import com.example.order.presentation.http.response.QueryOrderResponse;

import java.math.BigDecimal;

public class OrderConverter {
    public static CreateOrderInput toCreateOrderInput(final CreateOrderRequest request) {
        return CreateOrderInput
                .builder()
                .id(null)
                .customerId(request.getCustomerId())
                .items(request.getItems().stream().map(OrderConverter::toModel).toList())
                .currency(request.getCurrency())
                .paymentMethod(toModel(request.getPaymentMethod()))
                .shippingAddress(toModel(request.getShippingAddress()))
                .billingAddress(toModel(request.getBillingAddress()))
                .build();
    }

    public static QueryOrderInput toQueryOrderInput(final Long id) {
        return QueryOrderInput
                .builder()
                .id(id)
                .build();
    }

    public static QueryOrdersInput toQueryOrdersInput(final QueryOrdersRequest request) {
        return QueryOrdersInput
                .builder()
                .page(request.getPage())
                .size(request.getSize())
                .customerId(request.getCustomerId())
                .build();
    }

    public static ModifyOrderStatusInput toModifyOrderStatusInput(final ModifyOrderStatusRequest request) {
        return ModifyOrderStatusInput
                .builder()
                .id(request.getId())
                .status(toModel(request.getStatus()))
                .build();
    }

    public static CancelOrderInput toCancelOrderInput(final Long id) {
        return CancelOrderInput
                .builder()
                .id(id)
                .build();
    }

    public static QueryOrderResponse toQueryOrderResponse(final QueryOrderOutputData outputData) {
        return QueryOrderResponse
                .builder()
                .id(outputData.getId())
                .createTime(outputData.getCreateTime())
                .orderNumber(outputData.getOrderNumber())
                .customerId(outputData.getCustomerId())
                .items(outputData.getItems().stream().map(OrderConverter::toDto).toList())
                .totalAmount(outputData.getTotalAmount())
                .currency(outputData.getCurrency())
                .status(toDto(outputData.getStatus()))
                .paymentMethod(toDto(outputData.getPaymentMethod()))
                .shippingAddress(toDto(outputData.getShippingAddress()))
                .billingAddress(toDto(outputData.getBillingAddress()))
                .orderDate(outputData.getOrderDate())
                .shippedDate(outputData.getShippedDate())
                .deliveredDate(outputData.getDeliveredDate())
                .build();
    }

    private static OrderItemVoModel toModel(final OrderItemDto dto) {
        if (dto == null) return null;
        final BigDecimal totalPrice = dto.getUnitPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));
        return OrderItemVoModel
                .builder()
                .productId(dto.getProductId())
                .productName(dto.getProductName())
                .quantity(dto.getQuantity())
                .unitPrice(dto.getUnitPrice())
                .totalPrice(totalPrice)
                .build();
    }

    private static AddressVoModel toModel(final AddressDto dto) {
        if (dto == null) return null;
        return AddressVoModel
                .builder()
                .street(dto.getStreet())
                .city(dto.getCity())
                .state(dto.getState())
                .zipCode(dto.getZipCode())
                .country(dto.getCountry())
                .build();
    }

    private static PaymentMethodEnuModel toModel(final PaymentMethodEnuDto dto) {
        if (dto == null) return null;
        return PaymentMethodEnuModel.valueOf(dto.name());
    }

    private static OrderStatusEnuModel toModel(final OrderStatusEnuDto dto) {
        if (dto == null) return null;
        return OrderStatusEnuModel.valueOf(dto.name());
    }

    private static OrderItemDto toDto(final OrderItemVoModel model) {
        if (model == null) return null;
        return OrderItemDto
                .builder()
                .productId(model.getProductId())
                .productName(model.getProductName())
                .quantity(model.getQuantity())
                .unitPrice(model.getUnitPrice())
                .totalPrice(model.getTotalPrice())
                .build();
    }

    private static AddressDto toDto(final AddressVoModel model) {
        if (model == null) return null;
        return AddressDto
                .builder()
                .street(model.getStreet())
                .city(model.getCity())
                .state(model.getState())
                .zipCode(model.getZipCode())
                .country(model.getCountry())
                .build();
    }

    private static OrderStatusEnuDto toDto(final OrderStatusEnuModel model) {
        if (model == null) return null;
        return OrderStatusEnuDto.valueOf(model.name());
    }

    private static PaymentMethodEnuDto toDto(final PaymentMethodEnuModel model) {
        if (model == null) return null;
        return PaymentMethodEnuDto.valueOf(model.name());
    }
}