package com.example.common.ca.domain;

public interface WritableRepository<T, ID> {
    T save(T entity);

    void markDeleted(T entity);
}
