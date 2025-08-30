package com.example.member.infrastructure.eventbus;

import com.example.common.ca.EventBus;
import com.example.common.ca.domain.Event;
import com.example.member.domain.event.CreateMemberEvent;
import com.example.member.domain.event.ModifyMemberEvent;
import com.example.member.domain.event.QueryMemberEvent;
import com.example.member.domain.event.RemoveMemberEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventBusImpl implements EventBus {
    @Override
    public void publishAsync(final Event<?> event) {
        try {
            switch (event) {
                case CreateMemberEvent createMemberEvent -> log.info("CreateMemberEvent: {}", createMemberEvent);
                case QueryMemberEvent queryMemberEvent -> log.info("QueryMemberEvent: {}", queryMemberEvent);
                case ModifyMemberEvent modifyMemberEvent -> log.info("ModifyMemberEvent: {}", modifyMemberEvent);
                case RemoveMemberEvent removeMemberEvent -> log.info("RemoveMemberEvent: {}", removeMemberEvent);
                default -> log.warn("Unknown event type: {}", event.getClass().getSimpleName());
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
