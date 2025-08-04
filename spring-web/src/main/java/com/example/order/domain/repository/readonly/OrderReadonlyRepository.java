package com.example.order.domain.repository.readonly;

import com.example.common.ca.domain.ReadonlyRepository;
import com.example.common.data.Pagination;

public interface OrderReadonlyRepository<T, ID> extends ReadonlyRepository<T, ID> {
    Pagination<T> findAll(
            final Integer pageNumber,
            final Integer pageSize
    );

    Pagination<T> findByCustomerId(
            final Long customerId,
            final Integer pageNumber,
            final Integer pageSize
    );
}