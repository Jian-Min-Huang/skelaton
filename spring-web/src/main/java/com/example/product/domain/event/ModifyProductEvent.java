package com.example.product.domain.event;

import com.example.common.ca.domain.Event;
import com.example.product.domain.entity.Product;

public class ModifyProductEvent extends Event<Product> {
    private Product entity;

    @Override
    public Product extractEntity() {
        return this.entity;
    }
}