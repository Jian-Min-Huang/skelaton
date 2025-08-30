package com.example.member.domain.event;

import com.example.common.ca.domain.Event;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class QueryMembersEvent extends Event<List<Long>> {
    private List<Long> entityIds;

    @Override
    public List<Long> extractEntity() {
        return entityIds;
    }
}
