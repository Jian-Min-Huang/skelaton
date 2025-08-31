package com.example.member.domain.event;

import com.example.common.ddd.domain.DomainEvent;
import com.example.member.domain.entity.Member;
import lombok.Builder;
import lombok.ToString;

@Builder(toBuilder = true)
@ToString
public class ActivateMemberEvent implements DomainEvent<Member> {
    private Member originalEntity;
    private Member entity;

    @Override
    public Member extractEntity() {
        return this.entity;
    }
}
