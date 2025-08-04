package com.example.product.application.port.input;

import com.example.common.ca.cqrs.CqrsInput;
import com.example.product.application.adapter.vo.PriceVoModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ModifyProductPriceInput extends CqrsInput<Long> {
    private Long id;
    private PriceVoModel price;
}