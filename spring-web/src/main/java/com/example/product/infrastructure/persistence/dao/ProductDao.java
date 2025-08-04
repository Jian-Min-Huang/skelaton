package com.example.product.infrastructure.persistence.dao;

import com.example.product.infrastructure.persistence.po.ProductPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductDao extends JpaRepository<ProductPo, Long>, JpaSpecificationExecutor<ProductPo> {
}