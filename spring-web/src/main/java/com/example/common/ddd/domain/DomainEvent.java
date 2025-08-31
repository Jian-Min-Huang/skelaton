package com.example.common.ddd.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public interface DomainEvent<T> extends Serializable {
    default UUID eventId() {
        return UUID.randomUUID();
    }

    default Instant occurredAt() {
        return Instant.now();
    }

    T extractEntity();
}
