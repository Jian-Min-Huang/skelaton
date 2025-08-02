package com.example.member.service.infrastructure.data.repository;

import com.example.member.application.adapter.entity.MemberModel;
import com.example.member.application.adapter.repository.writable.MemberWritableRepository;
import com.example.member.service.infrastructure.data.persistence.dao.MemberDao;
import com.example.member.service.infrastructure.data.persistence.mapper.MemberMapper;
import com.example.member.service.infrastructure.data.persistence.po.MemberPo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberWritableRepositoryImpl implements MemberWritableRepository<MemberModel, Long> {
    private final MemberDao memberDao;

    @Override
    public void modifyEmail(final MemberModel data) {

    }

    @Override
    public void remove(final Long id) {

    }

    @Override
    public MemberModel save(final MemberModel data) {
        final MemberPo po = MemberMapper.toPo(data);
        final MemberPo save = memberDao.save(po);

        return MemberMapper.toModel(save);
    }

    @Override
    public void markDeleted(final Long id) {

    }
}
