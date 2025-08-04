package com.example.order.presentation.http.response;

import com.example.order.presentation.http.dto.AddressDto;
import com.example.order.presentation.http.dto.OrderItemDto;
import com.example.order.presentation.http.dto.enu.OrderStatusEnuDto;
import com.example.order.presentation.http.dto.enu.PaymentMethodEnuDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryOrderResponse {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "2024-01-01T00:00:00Z")
    private Instant createTime;
    @Schema(example = "ORD-12345678")
    private String orderNumber;
    @Schema(example = "1")
    private Long customerId;
    private List<OrderItemDto> items;
    @Schema(example = "1999.98")
    private BigDecimal totalAmount;
    @Schema(example = "USD")
    private String currency;
    private OrderStatusEnuDto status;
    private PaymentMethodEnuDto paymentMethod;
    private AddressDto shippingAddress;
    private AddressDto billingAddress;
    @Schema(example = "2024-01-01T00:00:00Z")
    private Instant orderDate;
    @Schema(example = "2024-01-02T00:00:00Z")
    private Instant shippedDate;
    @Schema(example = "2024-01-03T00:00:00Z")
    private Instant deliveredDate;
}