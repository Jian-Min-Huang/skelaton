package com.example.order.presentation.http.response.converter;

import com.example.order.application.query.output.CartCqrsQueryOutput;
import com.example.order.presentation.http.response.CartResponseDTO;
import com.example.shared.presentation.http.ResponseConverter;
import org.springframework.stereotype.Component;

@Component
public class CartResponseConverter implements ResponseConverter {
    public CartResponseDTO toResponse(final CartCqrsQueryOutput output) {
        return new CartResponseDTO(
                output.id(),
                output.customerId(),
                output.statusName(),
                output.couponCode(),
                output.discountAmount(),
                output.discountCurrency() != null ? output.discountCurrency().getCurrencyCode() : null
        );
    }
}
