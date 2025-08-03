package com.example.member.domain.repository.readonly;

import com.example.common.ca.domain.ReadonlyRepository;
import com.example.common.data.Pagination;
import com.example.member.application.adapter.vo.enu.MemberStatusEnuModel;

import java.util.List;

public interface MemberReadonlyRepository<T, ID> extends ReadonlyRepository<T, ID> {
    Pagination<T> findAll(
            final Integer registeredInXDays,
            final List<MemberStatusEnuModel> statusList,
            final Integer pageNumber,
            final Integer pageSize
    );
}
