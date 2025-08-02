package com.example.member.application.usecase.query;

import com.example.common.ca.adapter.EventBus;
import com.example.common.ca.cqrs.CqrsInput;
import com.example.common.ca.cqrs.CqrsOutput;
import com.example.common.ca.cqrs.CqrsTemplate;
import com.example.member.application.adapter.entity.MemberModel;
import com.example.member.application.adapter.repository.readonly.MemberReadonlyRepository;
import com.example.member.application.usecase.port.input.QueryMemberInput;
import com.example.member.application.usecase.port.input.QueryMembersInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class MemberQueryUseCase implements CqrsTemplate {
    private final EventBus eventBus;
    private final MemberReadonlyRepository<MemberModel, Long> memberReadonlyRepository;

    @Override
    public CqrsOutput<?> execute(final CqrsInput<?> input) {
        try {
            if (input instanceof QueryMemberInput queryMemberInput) {
                final Optional<MemberModel> memberModel = memberReadonlyRepository.findById(queryMemberInput.getId());

                return memberModel
                        .map(CqrsOutput::success)
                        .orElseGet(() -> CqrsOutput.failure("Member not found, ID: " + queryMemberInput.getId()));
            } else if (input instanceof QueryMembersInput queryMembersInput) {
                return CqrsOutput.failure(MemberQueryUseCase.class.getSimpleName() + " Invalid Input: " + input.toString());
            } else {
                return CqrsOutput.failure(MemberQueryUseCase.class.getSimpleName() + " Invalid Input: " + input.toString());
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);

            return CqrsOutput.failure(ex.getMessage());
        }
    }
}
