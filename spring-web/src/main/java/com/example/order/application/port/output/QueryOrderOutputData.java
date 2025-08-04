package com.example.order.application.port.output;

import com.example.order.application.adapter.vo.AddressVoModel;
import com.example.order.application.adapter.vo.OrderItemVoModel;
import com.example.order.application.adapter.vo.enu.OrderStatusEnuModel;
import com.example.order.application.adapter.vo.enu.PaymentMethodEnuModel;
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
public class QueryOrderOutputData {
    private Long id;
    private Instant createTime;
    private String orderNumber;
    private Long customerId;
    private List<OrderItemVoModel> items;
    private BigDecimal totalAmount;
    private String currency;
    private OrderStatusEnuModel status;
    private PaymentMethodEnuModel paymentMethod;
    private AddressVoModel shippingAddress;
    private AddressVoModel billingAddress;
    private Instant orderDate;
    private Instant shippedDate;
    private Instant deliveredDate;
}