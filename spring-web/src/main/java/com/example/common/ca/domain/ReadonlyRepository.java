package com.example.common.ca.domain;

import java.util.Optional;

public interface ReadonlyRepository<T, ID> {
    Optional<T> findById(ID id);
}
