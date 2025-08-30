package com.example.member.application.usecase.command;

import com.example.common.ca.EventBus;
import com.example.common.ca.cqrs.CqrsInput;
import com.example.common.ca.cqrs.CqrsOutput;
import com.example.common.ca.cqrs.CqrsTemplate;
import com.example.member.application.adapter.projector.MemberProjector;
import com.example.member.application.port.input.CreateMemberInputData;
import com.example.member.application.port.input.ModifyMemberEmailInputData;
import com.example.member.application.port.input.RemoveMemberInputData;
import com.example.member.domain.entity.Member;
import com.example.member.domain.repository.writable.MemberWritableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MemberCommandUseCase implements CqrsTemplate {
    private final EventBus eventBus;
    private final MemberWritableRepository<Member, Long> memberWritableRepository;

    @Override
    public CqrsOutput<?> execute(final CqrsInput<?> input) {
        try {
            switch (input) {
                case CreateMemberInputData createMemberInput -> {
                    final Member entity = memberWritableRepository.save(MemberProjector.toEntity(createMemberInput));
                    return CqrsOutput.success(entity.getId());
                }
                case ModifyMemberEmailInputData modifyMemberEmailInput -> {
                    memberWritableRepository.modifyEmail(MemberProjector.toEntity(modifyMemberEmailInput));
                    return CqrsOutput.success(modifyMemberEmailInput.getId());
                }
                case RemoveMemberInputData removeMemberInput -> {
                    memberWritableRepository.remove(removeMemberInput.getId());
                    return CqrsOutput.success(removeMemberInput.getId());
                }
                default -> {
                    return CqrsOutput.failure(MemberCommandUseCase.class.getSimpleName() + " Invalid Input: " + input);
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);

            return CqrsOutput.failure(ex.getMessage());
        }
    }
}
