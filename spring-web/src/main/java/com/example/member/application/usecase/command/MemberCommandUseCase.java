package com.example.member.application.usecase.command;

import com.example.common.ca.EventBus;
import com.example.common.ca.cqrs.CqrsInput;
import com.example.common.ca.cqrs.CqrsOutput;
import com.example.common.ca.cqrs.CqrsTemplate;
import com.example.member.application.port.input.CreateMemberInputData;
import com.example.member.application.port.input.ModifyMemberEmailInputData;
import com.example.member.application.port.input.RemoveMemberInputData;
import com.example.member.domain.entity.Member;
import com.example.member.domain.event.CreatedMemberEvent;
import com.example.member.domain.repository.readonly.MemberReadonlyRepository;
import com.example.member.domain.repository.writable.MemberWritableRepository;
import com.example.member.domain.vo.PhoneNumber;
import com.example.member.domain.vo.enu.Gender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MemberCommandUseCase implements CqrsTemplate {
    private final EventBus eventBus;
    private final MemberReadonlyRepository<Member, Long> memberReadonlyRepository;
    private final MemberWritableRepository<Member, Long> memberWritableRepository;

    @Override
    public CqrsOutput<?> execute(final CqrsInput<?> input) {
        try {
            switch (input) {
                case CreateMemberInputData createMemberInput -> {
                    final CreatedMemberEvent event = Member
                        .create(
                            createMemberInput.getFirstName(),
                            createMemberInput.getLastName(),
                            createMemberInput.getEmail(),
                            PhoneNumber.builder().countryCode(createMemberInput.getPhoneNumber().getCountryCode()).number(createMemberInput.getPhoneNumber().getNumber()).build(),
                            Gender.valueOf(createMemberInput.getGender().name())
                        );
                    final Member saveEntity = memberWritableRepository.save(event.extractEntity());
                    eventBus.publishAsync(event);

                    return CqrsOutput.success(saveEntity.getId());
                }
                case ModifyMemberEmailInputData modifyMemberEmailInput -> {
                    return memberReadonlyRepository
                        .findById(modifyMemberEmailInput.id)
                        .map(entity -> entity.modifyEmail(modifyMemberEmailInput.getEmail()))
                        .map(event -> {
                            final Member entity = event.extractEntity();
                            memberWritableRepository.modifyEmail(entity);
                            eventBus.publishAsync(event);

                            return CqrsOutput.success(entity.getId());
                        })
                        .orElse(CqrsOutput.failure(""));
                }
                case RemoveMemberInputData removeMemberInput -> {
                    return memberReadonlyRepository
                        .findById(removeMemberInput.id)
                        .map(Member::remove)
                        .map(event -> {
                            final Member entity = event.extractEntity();
                            memberWritableRepository.markDeleted(entity);
                            eventBus.publishAsync(event);

                            return CqrsOutput.success(entity.getId());
                        })
                        .orElse(CqrsOutput.failure(""));
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
