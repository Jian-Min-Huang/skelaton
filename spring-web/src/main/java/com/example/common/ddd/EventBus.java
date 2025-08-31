package com.example.common.ddd;

import com.example.common.ddd.domain.DomainEvent;

public interface EventBus {
    void publishAsync(DomainEvent<?> event);
}
