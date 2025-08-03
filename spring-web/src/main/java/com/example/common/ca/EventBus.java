package com.example.common.ca;

import com.example.common.ca.domain.Event;

public interface EventBus {
    void publishAsync(Event<?> event);
}
