package com.example.member.service.infrastructure.eventbus;

import com.example.common.ca.adapter.EventBus;
import com.example.common.ca.adapter.EventModel;
import org.springframework.stereotype.Component;

@Component
public class EventBusImpl implements EventBus {
    @Override
    public void publishAsync(final EventModel<?> event) {

    }
}
