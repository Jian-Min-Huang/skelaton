package com.example.member.application.port.input;

import com.example.common.ca.cqrs.CqrsInput;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ModifyMemberEmailInputData extends CqrsInput<Long> {
    private String email;
}
