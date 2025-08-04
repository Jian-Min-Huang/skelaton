package com.example.member.infrastructure.repository;

import com.example.member.domain.entity.Member;
import com.example.member.domain.repository.writable.MemberWritableRepository;
import com.example.member.infrastructure.persistence.dao.MemberDao;
import com.example.member.infrastructure.persistence.mapper.MemberMapper;
import com.example.member.infrastructure.persistence.po.MemberPo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
@RequiredArgsConstructor
public class MemberWritableRepositoryImpl implements MemberWritableRepository<Member, Long> {
    private final MemberDao memberDao;

    @Override
    public void modifyEmail(final Member data) {
        memberDao
                .findById(data.getId())
                .ifPresent(element -> {
                    element.setEmail(data.getEmail());
                    element.setLastModifyTime(Instant.now());

                    memberDao.save(element);
                });
    }

    @Override
    public void remove(final Long id) {
        memberDao.deleteById(id);
    }

    @Override
    public Member save(final Member data) {
        final MemberPo po = MemberMapper.toNewPo(data);
        final MemberPo save = memberDao.save(po);

        return MemberMapper.toEntity(save);
    }

    @Override
    public void markDeleted(final Long id) {
        memberDao
                .findById(id)
                .ifPresent(element -> {
                    element.setDeleted(1);
                    element.setDeleteTime(Instant.now());

                    memberDao.save(element);
                });
    }
}
