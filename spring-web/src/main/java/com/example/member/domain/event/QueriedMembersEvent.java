package com.example.member.domain.event;

import com.example.common.ca.domain.DomainEvent;
import lombok.Builder;
import lombok.ToString;

import java.util.List;

@Builder(toBuilder = true)
@ToString
public class QueriedMembersEvent implements DomainEvent<List<Long>> {
    private List<Long> entityIds;

    @Override
    public List<Long> extractEntity() {
        return entityIds;
    }
}
