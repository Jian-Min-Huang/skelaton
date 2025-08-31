package com.example.member.infrastructure.eventbus;

import com.example.common.ddd.EventBus;
import com.example.common.ddd.domain.DomainEvent;
import com.example.member.domain.event.CreatedMemberEvent;
import com.example.member.domain.event.ModifiedMemberEvent;
import com.example.member.domain.event.QueriedMemberEvent;
import com.example.member.domain.event.QueriedMembersEvent;
import com.example.member.domain.event.RemovedMemberEvent;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class MemberEventBusImpl implements EventBus {
    private final ExecutorService executorService = new ThreadPoolExecutor(
        Runtime.getRuntime().availableProcessors(),
        (Runtime.getRuntime().availableProcessors() * 2) + 1,
        30L,
        TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(1000)
    );

    @Override
    public void publishAsync(final DomainEvent<?> event) {
        executorService
            .submit(() -> {
                try {
                    switch (event) {
                        case CreatedMemberEvent createdMemberEvent -> log.info("CreatedMemberEvent: {}", createdMemberEvent);
                        case QueriedMemberEvent queriedMemberEvent -> log.info("QueriedMemberEvent: {}", queriedMemberEvent);
                        case QueriedMembersEvent queriedMembersEvent -> log.info("QueriedMembersEvent: {}", queriedMembersEvent);
                        case ModifiedMemberEvent modifiedMemberEvent -> log.info("ModifiedMemberEvent: {}", modifiedMemberEvent);
                        case RemovedMemberEvent removedMemberEvent -> log.info("RemovedMemberEvent: {}", removedMemberEvent);
                        default -> log.warn("Unknown event type: {}", event.getClass().getSimpleName());
                    }
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
            });
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
    }
}
