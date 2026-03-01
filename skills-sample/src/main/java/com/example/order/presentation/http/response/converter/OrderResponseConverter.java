package com.example.order.presentation.http.response.converter;

import com.example.order.application.query.output.OrderCqrsQueryOutput;
import com.example.order.presentation.http.response.OrderResponseDTO;
import com.example.shared.presentation.http.ResponseConverter;
import org.springframework.stereotype.Component;

@Component
public class OrderResponseConverter implements ResponseConverter {
    public OrderResponseDTO toResponse(final OrderCqrsQueryOutput output) {
        return new OrderResponseDTO(
                output.id(),
                output.orderNumber(),
                output.customerId(),
                output.recipientName(),
                output.phone(),
                output.city(),
                output.district(),
                output.street(),
                output.zipCode(),
                output.totalAmount(),
                output.totalCurrency() != null ? output.totalCurrency().getCurrencyCode() : null,
                output.statusName(),
                output.paymentMethodName()
        );
    }
}
