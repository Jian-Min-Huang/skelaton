package com.example.common.ca.domain;

import java.time.Instant;
import java.util.UUID;

public abstract class Event<T> {
    UUID eventId = UUID.randomUUID();
    Instant occurredAt = Instant.now();

    public abstract T extractEntity();
}
