package com.example.common.ca.adapter;

public interface EventBus {
    void publishAsync(EventModel<?> event);
}
