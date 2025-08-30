package com.example.common.ca;

import com.example.common.ca.domain.DomainEvent;

public interface EventBus {
    void publishAsync(DomainEvent<?> event);
}
