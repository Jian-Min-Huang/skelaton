package com.example.member.domain.event;

import com.example.common.ca.domain.DomainEvent;
import com.example.member.domain.entity.Member;
import lombok.Builder;
import lombok.ToString;

@Builder(toBuilder = true)
@ToString
public class ModifiedMemberEvent implements DomainEvent<Member> {
    private Member entity;

    @Override
    public Member extractEntity() {
        return this.entity;
    }
}
