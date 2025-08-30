package com.example.member.domain.event;

import com.example.common.ca.domain.Event;
import com.example.member.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RemoveMemberEvent extends Event<Member> {
    private Member entity;

    @Override
    public Member extractEntity() {
        return this.entity;
    }
}
