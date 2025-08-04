package com.example.order.infrastructure.repository;

import com.example.common.data.Pagination;
import com.example.order.domain.entity.Order;
import com.example.order.domain.repository.readonly.OrderReadonlyRepository;
import com.example.order.infrastructure.persistence.dao.OrderDao;
import com.example.order.infrastructure.persistence.mapper.OrderMapper;
import com.example.order.infrastructure.persistence.po.OrderPo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderReadonlyRepositoryImpl implements OrderReadonlyRepository<Order, Long> {
    private final OrderDao orderDao;

    @Override
    public Optional<Order> findById(final Long id) {
        return orderDao.findById(id).map(OrderMapper::toEntity);
    }

    @Override
    public Pagination<Order> findAll(final Integer pageNumber, final Integer pageSize) {
        final List<Order> content = orderDao
                .findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")))
                .stream()
                .map(OrderMapper::toEntity)
                .toList();
        final Long totalElements = orderDao.count();

        return Pagination
                .<Order>builder()
                .content(content)
                .currentPage(pageNumber)
                .pageSize(pageSize)
                .totalPages((int) Math.ceil((double) totalElements / pageSize))
                .totalElements(totalElements)
                .build();
    }

    @Override
    public Pagination<Order> findByCustomerId(final Long customerId, final Integer pageNumber, final Integer pageSize) {
        final Specification<OrderPo> spec = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("customerId"), customerId);

        final List<Order> content = orderDao
                .findAll(spec, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")))
                .stream()
                .map(OrderMapper::toEntity)
                .toList();
        final Long totalElements = orderDao.count(spec);

        return Pagination
                .<Order>builder()
                .content(content)
                .currentPage(pageNumber)
                .pageSize(pageSize)
                .totalPages((int) Math.ceil((double) totalElements / pageSize))
                .totalElements(totalElements)
                .build();
    }
}