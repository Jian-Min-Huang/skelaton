package com.example.order.presentation.http.request;

import com.example.order.presentation.http.dto.AddressDto;
import com.example.order.presentation.http.dto.OrderItemDto;
import com.example.order.presentation.http.dto.enu.PaymentMethodEnuDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long customerId;
    @Valid
    @NotEmpty
    private List<OrderItemDto> items;
    @Schema(example = "USD", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String currency;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private PaymentMethodEnuDto paymentMethod;
    @Valid
    @NotNull
    private AddressDto shippingAddress;
    @Valid
    @NotNull
    private AddressDto billingAddress;
}