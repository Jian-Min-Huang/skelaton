package com.example.member.application.port.input;

import com.example.common.ddd.cqrs.CqrsInput;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
public class RemoveMemberInputData implements CqrsInput<Long> {
    private Long id;

    @Override
    public Long getId() {
        return id;
    }
}
