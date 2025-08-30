package com.example.member.application.port.input;

import com.example.common.ca.cqrs.CqrsInput;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryMemberInputData extends CqrsInput<Long> {
}
