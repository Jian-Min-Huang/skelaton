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
    public void modifyEmail(final Member entity) {
        memberDao
            .findById(entity.getId())
            .ifPresent(po -> {
                po.setEmail(entity.getEmail());
                po.setLastModifyTime(Instant.now());

                memberDao.save(po);
            });
    }

    @Override
    public Member save(final Member entity) {
        final MemberPo po = MemberMapper.toNewPo(entity);
        final MemberPo save = memberDao.save(po);

        return MemberMapper.toEntity(save);
    }

    @Override
    public void markDeleted(final Member entity) {
        memberDao
            .findById(entity.getId())
            .ifPresent(po -> {
                po.setDeleteTime(entity.getDeleteTime());
                po.setDeleted(entity.getDeleted());

                memberDao.save(po);
            });
    }
}
