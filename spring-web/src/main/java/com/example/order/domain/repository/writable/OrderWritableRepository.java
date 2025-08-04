package com.example.order.domain.repository.writable;

import com.example.common.ca.domain.WritableRepository;

public interface OrderWritableRepository<T, ID> extends WritableRepository<T, ID> {
    void modifyStatus(T data);

    void modifyShippingAddress(T data);

    void cancel(ID id);
}