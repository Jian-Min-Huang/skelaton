package com.example.shared.domain;

import java.time.Instant;

public interface DomainEvent {
    Instant occurredAt();
}
