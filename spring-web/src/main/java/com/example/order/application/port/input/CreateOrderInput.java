package com.example.order.application.port.input;

import com.example.common.ca.cqrs.CqrsInput;
import com.example.order.application.adapter.vo.AddressVoModel;
import com.example.order.application.adapter.vo.OrderItemVoModel;
import com.example.order.application.adapter.vo.enu.PaymentMethodEnuModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateOrderInput extends CqrsInput<Long> {
    private Long customerId;
    private List<OrderItemVoModel> items;
    private String currency;
    private PaymentMethodEnuModel paymentMethod;
    private AddressVoModel shippingAddress;
    private AddressVoModel billingAddress;
}