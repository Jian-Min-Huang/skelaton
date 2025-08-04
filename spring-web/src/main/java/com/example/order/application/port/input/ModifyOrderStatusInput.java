package com.example.order.application.port.input;

import com.example.common.ca.cqrs.CqrsInput;
import com.example.order.application.adapter.vo.enu.OrderStatusEnuModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ModifyOrderStatusInput extends CqrsInput<Long> {
    private Long id;
    private OrderStatusEnuModel status;
}