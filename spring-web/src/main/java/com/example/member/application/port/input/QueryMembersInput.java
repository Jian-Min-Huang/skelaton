package com.example.member.application.port.input;

import com.example.common.ca.cqrs.CqrsInput;
import com.example.member.application.adapter.vo.enu.MemberStatusEnuModel;
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
    private List<MemberStatusEnuModel> statusList;
    private Integer pageNumber;
    private Integer pageSize;
}
