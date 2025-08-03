package com.example.member.infrastructure.data.repository;

import com.example.member.domain.entity.Member;
import com.example.member.domain.repository.writable.MemberWritableRepository;
import com.example.member.infrastructure.data.persistence.dao.MemberDao;
import com.example.member.infrastructure.data.persistence.mapper.MemberMapper;
import com.example.member.infrastructure.data.persistence.po.MemberPo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberWritableRepositoryImpl implements MemberWritableRepository<Member, Long> {
    private final MemberDao memberDao;

    @Override
    public void modifyEmail(final Member data) {

    }

    @Override
    public void remove(final Long id) {

    }

    @Override
    public Member save(final Member data) {
        final MemberPo po = MemberMapper.toPo(data);
        final MemberPo save = memberDao.save(po);

        return MemberMapper.toModel(save);
    }

    @Override
    public void markDeleted(final Long id) {

    }
}
