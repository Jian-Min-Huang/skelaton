package com.example.product.domain.entity;

import com.example.common.ca.domain.Entity;
import com.example.product.domain.vo.Price;
import com.example.product.domain.vo.enu.Category;
import com.example.product.domain.vo.enu.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends Entity<Long> {
    private String name;
    private String description;
    private String sku;
    private Price price;
    private Category category;
    private Integer stockQuantity;
    private ProductStatus status;
}