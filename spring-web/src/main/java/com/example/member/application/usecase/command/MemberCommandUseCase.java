package com.example.member.application.usecase.command;

import com.example.common.ca.adapter.EventBus;
import com.example.common.ca.cqrs.CqrsInput;
import com.example.common.ca.cqrs.CqrsOutput;
import com.example.common.ca.cqrs.CqrsTemplate;
import com.example.member.application.adapter.entity.MemberModel;
import com.example.member.application.adapter.projector.MemberProjector;
import com.example.member.application.adapter.repository.writable.MemberWritableRepository;
import com.example.member.application.usecase.port.input.CreateMemberInput;
import com.example.member.application.usecase.port.input.ModifyMemberEmailInput;
import com.example.member.application.usecase.port.input.RemoveMemberInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MemberCommandUseCase implements CqrsTemplate {
    private final EventBus eventBus;
    private final MemberWritableRepository<MemberModel, Long> memberWritableRepository;

    @Override
    public CqrsOutput<?> execute(final CqrsInput<?> input) {
        try {
            switch (input) {
                case CreateMemberInput createMemberInput -> {
                    final MemberModel model = memberWritableRepository.save(MemberProjector.toMemberModel(createMemberInput));
                    return CqrsOutput.success(model);
                }
                case ModifyMemberEmailInput modifyMemberEmailInput -> {
                    memberWritableRepository.modifyEmail(MemberProjector.toMemberModel(modifyMemberEmailInput));
                    return CqrsOutput.success(modifyMemberEmailInput.getId());
                }
                case RemoveMemberInput removeMemberInput -> {
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
