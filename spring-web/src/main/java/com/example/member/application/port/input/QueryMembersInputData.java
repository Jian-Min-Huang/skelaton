package com.example.member.application.port.input;

import com.example.common.ddd.cqrs.CqrsInput;
import com.example.member.application.adapter.vo.enu.MemberStatusEnuModel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
public class QueryMembersInputData implements CqrsInput<Long> {
    private Integer registeredInXDays;
    private List<MemberStatusEnuModel> statuses;
    private Integer pageNumber;
    private Integer pageSize;

    @Override
    public Long getId() {
        return 0L;
    }
}
