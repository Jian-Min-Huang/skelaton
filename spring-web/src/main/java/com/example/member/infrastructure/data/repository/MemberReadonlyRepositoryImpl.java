package com.example.member.infrastructure.data.repository;

import com.example.common.data.Pagination;
import com.example.member.application.adapter.vo.enu.MemberStatusEnuModel;
import com.example.member.domain.entity.Member;
import com.example.member.domain.repository.readonly.MemberReadonlyRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberReadonlyRepositoryImpl implements MemberReadonlyRepository<Member, Long> {
    @Override
    public Optional<Member> findById(final Long aLong) {
        return Optional.empty();
    }

    @Override
    public Pagination<Member> findAll(final Integer registeredInXDays, final List<MemberStatusEnuModel> statusList, final Integer pageNumber, final Integer pageSize) {
        return null;
    }
}
