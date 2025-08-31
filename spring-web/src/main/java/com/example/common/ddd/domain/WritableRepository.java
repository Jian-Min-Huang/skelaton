package com.example.common.ddd.domain;

public interface WritableRepository<T, ID> {
    T save(T entity);

    void markDeleted(T entity);
}
