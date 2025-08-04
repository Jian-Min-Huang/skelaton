package com.example.order.infrastructure.persistence.dao;

import com.example.order.infrastructure.persistence.po.OrderPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderDao extends JpaRepository<OrderPo, Long>, JpaSpecificationExecutor<OrderPo> {
}