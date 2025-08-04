package com.example.product.domain.repository.writable;

import com.example.common.ca.domain.WritableRepository;

public interface ProductWritableRepository<T, ID> extends WritableRepository<T, ID> {
    void modifyPrice(T data);

    void modifyStock(T data);

    void remove(ID id);
}