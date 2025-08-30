package com.example.member.application.port.input;

import com.example.common.ca.cqrs.CqrsInput;
import com.example.member.application.adapter.vo.enu.MemberStatusEnuModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class QueryMembersInputData extends CqrsInput<Long> {
    private Integer registeredInXDays;
    private List<MemberStatusEnuModel> statuses;
    private Integer pageNumber;
    private Integer pageSize;
}
