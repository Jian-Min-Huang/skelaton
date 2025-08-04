package com.example.product.domain.repository.readonly;

import com.example.common.ca.domain.ReadonlyRepository;
import com.example.common.data.Pagination;

public interface ProductReadonlyRepository<T, ID> extends ReadonlyRepository<T, ID> {
    Pagination<T> findAll(
            final Integer pageNumber,
            final Integer pageSize
    );
}