package com.example.product.application.port.input;

import com.example.common.ca.cqrs.CqrsInput;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RemoveProductInput extends CqrsInput<Long> {
    private Long id;
}