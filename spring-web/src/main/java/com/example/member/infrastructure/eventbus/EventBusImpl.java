package com.example.member.infrastructure.eventbus;

import com.example.common.ca.EventBus;
import com.example.common.ca.domain.DomainEvent;
import com.example.member.domain.event.CreatedMemberEvent;
import com.example.member.domain.event.ModifiedMemberEvent;
import com.example.member.domain.event.QueriedMemberEvent;
import com.example.member.domain.event.RemovedMemberEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventBusImpl implements EventBus {
    @Override
    public void publishAsync(final DomainEvent<?> event) {
        try {
            switch (event) {
                case CreatedMemberEvent createMemberEvent -> log.info("CreateMemberEvent: {}", createMemberEvent);
                case QueriedMemberEvent queryMemberEvent -> log.info("QueryMemberEvent: {}", queryMemberEvent);
                case ModifiedMemberEvent modifyMemberEvent -> log.info("ModifyMemberEvent: {}", modifyMemberEvent);
                case RemovedMemberEvent removeMemberEvent -> log.info("RemoveMemberEvent: {}", removeMemberEvent);
                default -> log.warn("Unknown event type: {}", event.getClass().getSimpleName());
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
