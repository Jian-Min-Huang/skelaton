package com.example.common.ca.domain;

public interface WritableRepository<T, ID> {
    T save(T data);

    void markDeleted(ID id);
}
