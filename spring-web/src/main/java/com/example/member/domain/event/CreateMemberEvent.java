package com.example.member.domain.event;

import com.example.common.ca.domain.Event;
import com.example.member.domain.entity.Member;

public class CreateMemberEvent extends Event<Member> {
    private Member entity;

    @Override
    public Member extractEntity() {
        return this.entity;
    }
}
