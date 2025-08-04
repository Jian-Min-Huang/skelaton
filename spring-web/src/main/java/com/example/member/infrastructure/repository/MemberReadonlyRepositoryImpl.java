package com.example.member.infrastructure.repository;

import com.example.common.data.Pagination;
import com.example.member.domain.entity.Member;
import com.example.member.domain.repository.readonly.MemberReadonlyRepository;
import com.example.member.domain.vo.enu.MemberStatus;
import com.example.member.infrastructure.persistence.dao.MemberDao;
import com.example.member.infrastructure.persistence.mapper.MemberMapper;
import com.example.member.infrastructure.persistence.po.MemberPo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberReadonlyRepositoryImpl implements MemberReadonlyRepository<Member, Long> {
    private final MemberDao memberDao;

    @Override
    public Optional<Member> findById(final Long id) {
        return memberDao.findById(id).map(MemberMapper::toEntity);
    }

    @Override
    public Pagination<Member> findAll(
            final Integer registeredInXDays,
            final List<MemberStatus> statuses,
            final Integer pageNumber,
            final Integer pageSize
    ) {
        final List<Member> content = memberDao
                .findAll(
                        buildSpec(registeredInXDays, statuses),
                        PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"))
                )
                .stream()
                .map(MemberMapper::toEntity)
                .toList();
        final Long totalElements = memberDao.count(buildSpec(registeredInXDays, statuses));

        return Pagination
                .<Member>builder()
                .content(content)
                .currentPage(pageNumber)
                .pageSize(pageSize)
                .totalPages((int) Math.ceil((double) totalElements / pageSize))
                .totalElements(totalElements)
                .build();
    }

    public Specification<MemberPo> buildSpec(final Integer registeredInXDays, final List<MemberStatus> statusList) {
        return Specification
                .where(hasRegisteredInXDays(registeredInXDays))
                .and(hasStatus(statusList));
    }

    private Specification<MemberPo> hasRegisteredInXDays(final Integer registeredInXDays) {
        return (root, query, criteriaBuilder) -> {
            if (registeredInXDays == null) {
                return null;
            }
            final Instant cutoffTime = Instant.now().minus(registeredInXDays, ChronoUnit.DAYS);
            return criteriaBuilder.greaterThan(root.get("createTime"), cutoffTime);
        };
    }

    private Specification<MemberPo> hasStatus(final List<MemberStatus> statusList) {
        return (root, query, criteriaBuilder) -> {
            if (statusList == null || statusList.isEmpty()) {
                return null;
            }
            return criteriaBuilder.in(root.get("status")).value(statusList.stream().map(Enum::name).toList());
        };
    }
}
