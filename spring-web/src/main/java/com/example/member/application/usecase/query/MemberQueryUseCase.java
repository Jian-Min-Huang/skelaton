package com.example.member.application.usecase.query;

import com.example.common.ca.EventBus;
import com.example.common.ca.cqrs.CqrsInput;
import com.example.common.ca.cqrs.CqrsOutput;
import com.example.common.ca.cqrs.CqrsTemplate;
import com.example.common.data.Pagination;
import com.example.member.application.adapter.projector.MemberProjector;
import com.example.member.application.port.input.QueryMemberInputData;
import com.example.member.application.port.input.QueryMembersInputData;
import com.example.member.domain.entity.Member;
import com.example.member.domain.repository.readonly.MemberReadonlyRepository;
import com.example.member.domain.vo.enu.MemberStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class MemberQueryUseCase implements CqrsTemplate {
    private final EventBus eventBus;
    private final MemberReadonlyRepository<Member, Long> memberReadonlyRepository;

    @Override
    public CqrsOutput<?> execute(final CqrsInput<?> input) {
        try {
            if (input instanceof QueryMemberInputData queryMemberInput) {
                final Optional<Member> entity = memberReadonlyRepository.findById(queryMemberInput.getId());

                return entity
                        .map(element -> CqrsOutput.success(MemberProjector.toOutput(element)))
                        .orElseGet(() -> CqrsOutput.failure("Member not found, ID: " + queryMemberInput.getId()));
            } else if (input instanceof QueryMembersInputData queryMembersInput) {
                final Pagination<Member> entities = memberReadonlyRepository.findAll(
                        queryMembersInput.getRegisteredInXDays(),
                        queryMembersInput.getStatuses().stream().map(element -> MemberStatus.valueOf(element.name())).toList(),
                        queryMembersInput.getPageNumber(),
                        queryMembersInput.getPageSize()
                );

                return CqrsOutput.success(MemberProjector.toOutput(entities));
            } else {
                return CqrsOutput.failure(MemberQueryUseCase.class.getSimpleName() + " Invalid Input: " + input.toString());
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);

            return CqrsOutput.failure(ex.getMessage());
        }
    }
}
