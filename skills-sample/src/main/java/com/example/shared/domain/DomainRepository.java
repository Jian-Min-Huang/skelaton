package com.example.shared.domain;

import java.util.Optional;
import java.util.UUID;

public interface DomainRepository<T extends DomainAggregateRoot> {
    T save(T aggregateRoot);
    Optional<T> queryById(UUID id);
    Integer removeById(UUID id);
    Boolean existsById(UUID id);
}
