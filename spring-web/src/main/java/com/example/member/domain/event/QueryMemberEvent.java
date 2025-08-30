package com.example.member.domain.event;

import com.example.common.ca.domain.Event;
import com.example.member.domain.entity.Member;

//@Builder(toBuilder = true)
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode(callSuper = true)
public class QueryMemberEvent extends Event<Member> {
    private Member entity;

    @Override
    public Member extractEntity() {
        return this.entity;
    }
}
