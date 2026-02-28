package com.example.shared.domain;

import java.util.List;
import java.util.Objects;

public record DomainResult<T>(T entity, List<DomainEvent> events) {
    public DomainResult {
        Objects.requireNonNull(entity, "entity must not be null");
        events = List.copyOf(events);
    }

    public static <T> DomainResult<T> of(final T entity, final DomainEvent... events) {
        return new DomainResult<>(entity, List.of(events));
    }

    public static <T> DomainResult<T> withoutEvents(final T entity) {
        return new DomainResult<>(entity, List.of());
    }
}
