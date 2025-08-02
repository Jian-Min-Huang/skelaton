package com.example.common.ca.adapter;

import java.time.Instant;
import java.util.UUID;

public abstract class EventModel<ID> {
    ID entityId;
    UUID eventId = UUID.randomUUID();
    Instant occurredAt = Instant.now();
}
