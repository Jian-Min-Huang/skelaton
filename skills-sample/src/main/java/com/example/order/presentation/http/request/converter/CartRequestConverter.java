package com.example.order.presentation.http.request.converter;

import com.example.order.application.command.AddCartItemCqrsCommand;
import com.example.order.application.command.CheckoutCqrsCommand;
import com.example.order.application.command.CreateCartCqrsCommand;
import com.example.order.presentation.http.request.AddCartItemRequestDTO;
import com.example.order.presentation.http.request.CheckoutRequestDTO;
import com.example.order.presentation.http.request.CreateCartRequestDTO;
import com.example.shared.presentation.http.RequestConverter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CartRequestConverter implements RequestConverter {
    public CreateCartCqrsCommand toCommand(final CreateCartRequestDTO request) {
        return new CreateCartCqrsCommand(
                request.getCustomerId()
        );
    }

    public AddCartItemCqrsCommand toCommand(final UUID cartId, final AddCartItemRequestDTO request) {
        return new AddCartItemCqrsCommand(
                cartId,
                request.getProductId(),
                request.getProductName(),
                request.getQuantity(),
                request.getUnitPrice(),
                request.getCurrency()
        );
    }

    public CheckoutCqrsCommand toCommand(final UUID cartId, final CheckoutRequestDTO request) {
        return new CheckoutCqrsCommand(
                cartId,
                request.getOrderNumber(),
                request.getRecipientName(),
                request.getPhone(),
                request.getCity(),
                request.getDistrict(),
                request.getStreet(),
                request.getZipCode(),
                request.getPaymentMethod()
        );
    }
}
