package com.example.inventory.domain.product.vo;

import com.example.shared.domain.DomainValueObject;

public record Sku(String code) implements DomainValueObject {
}
