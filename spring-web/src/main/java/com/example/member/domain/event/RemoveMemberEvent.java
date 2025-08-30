package com.example.member.domain.event;

import com.example.common.ca.domain.Event;
import com.example.member.domain.entity.Member;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RemoveMemberEvent extends Event<Member> {
    private Member entity;

    @Override
    public Member extractEntity() {
        return this.entity;
    }
}
