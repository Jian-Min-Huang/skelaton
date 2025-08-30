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
import com.example.member.domain.event.QueryMemberEvent;
import com.example.member.domain.event.QueryMembersEvent;
import com.example.member.domain.repository.readonly.MemberReadonlyRepository;
import com.example.member.domain.vo.enu.MemberStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MemberQueryUseCase implements CqrsTemplate {
    private final EventBus eventBus;
    private final MemberReadonlyRepository<Member, Long> memberReadonlyRepository;

    @Override
    public CqrsOutput<?> execute(final CqrsInput<?> input) {
        try {
            if (input instanceof QueryMemberInputData queryMemberInput) {
                return memberReadonlyRepository
                        .findById(queryMemberInput.id)
                        .map(entity -> {
                            eventBus.publishAsync(QueryMemberEvent.builder().entity(entity).build());

                            return CqrsOutput.success(MemberProjector.toOutput(entity));
                        })
                        .orElse(CqrsOutput.failure(""));
            } else if (input instanceof QueryMembersInputData queryMembersInput) {
                final Pagination<Member> entities = memberReadonlyRepository.findAll(
                        queryMembersInput.getRegisteredInXDays(),
                        queryMembersInput.getStatuses().stream().map(element -> MemberStatus.valueOf(element.name())).toList(),
                        queryMembersInput.getPageNumber(),
                        queryMembersInput.getPageSize()
                );

                eventBus.publishAsync(QueryMembersEvent.builder().entityIds(entities.getContent().stream().map(entity -> entity.id).toList()).build());

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
