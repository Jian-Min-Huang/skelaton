package com.example.product.application.port.input;

import com.example.common.ca.cqrs.CqrsInput;
import com.example.product.application.adapter.vo.PriceVoModel;
import com.example.product.application.adapter.vo.enu.CategoryEnuModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateProductInput extends CqrsInput<Long> {
    private String name;
    private String description;
    private String sku;
    private PriceVoModel price;
    private CategoryEnuModel category;
    private Integer stockQuantity;
}