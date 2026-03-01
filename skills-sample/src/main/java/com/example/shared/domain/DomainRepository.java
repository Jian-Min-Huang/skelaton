package com.example.shared.domain;

import java.util.Optional;

public interface DomainRepository<T extends DomainAggregateRoot> {
    T save(T aggregateRoot);
    Optional<T> queryById(Long id);
    Integer removeById(Long id);
    Boolean existsById(Long id);
}
