package com.example.member.application.usecase.port.input;

import com.example.common.ca.cqrs.CqrsInput;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class QueryMembersInput extends CqrsInput<Long> {
    private Integer registeredInXDays;
    private List<Integer> statusList;
    private Integer pageNumber;
    private Integer pageSize;
}
