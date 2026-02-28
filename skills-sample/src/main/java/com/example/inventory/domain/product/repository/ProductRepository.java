package com.example.inventory.domain.product.repository;

import com.example.inventory.domain.product.Product;

import java.util.Optional;

public interface ProductRepository {
    Product save(Product entity);
    Optional<Product> queryById(Long id);
    Integer removeById(Long id);
    Boolean existsById(Long id);
}
