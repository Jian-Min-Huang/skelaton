package com.example.member.application.usecase.command;

import com.example.common.ddd.EventBus;
import com.example.common.ddd.cqrs.CqrsOutput;
import com.example.common.ddd.cqrs.ExitCode;
import com.example.member.application.adapter.vo.PhoneNumberVoModel;
import com.example.member.application.adapter.vo.enu.GenderEnuModel;
import com.example.member.application.adapter.vo.enu.MemberStatusEnuModel;
import com.example.member.application.port.input.CreateMemberInputData;
import com.example.member.application.port.input.ModifyMemberEmailInputData;
import com.example.member.application.port.input.RemoveMemberInputData;
import com.example.member.domain.entity.Member;
import com.example.member.domain.repository.readonly.MemberReadonlyRepository;
import com.example.member.domain.repository.writable.MemberWritableRepository;
import com.example.member.domain.vo.PhoneNumber;
import com.example.member.domain.vo.enu.Gender;
import com.example.member.domain.vo.enu.MemberStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberCommandUseCaseTests {

    @Mock
    private EventBus eventBus;

    @Mock
    private MemberReadonlyRepository<Member, Long> memberReadonlyRepository;

    @Mock
    private MemberWritableRepository<Member, Long> memberWritableRepository;

    private MemberCommandUseCase memberCommandUseCase;

    @BeforeEach
    void setUp() {
        memberCommandUseCase = new MemberCommandUseCase(eventBus, memberReadonlyRepository, memberWritableRepository);
    }

    @Test
    void testCreateMemberCommand() {
        CreateMemberInputData input = CreateMemberInputData.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber(PhoneNumberVoModel.builder()
                        .countryCode("+886")
                        .number("123456789")
                        .build())
                .gender(GenderEnuModel.MALE)
                .status(MemberStatusEnuModel.ACTIVE)
                .build();

        Member savedMember = Member.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber(PhoneNumber.builder()
                        .countryCode("+886")
                        .number("123456789")
                        .build())
                .gender(Gender.MALE)
                .status(MemberStatus.INACTIVE)
                .createTime(Instant.now())
                .lastModifyTime(Instant.now())
                .deleted(0)
                .build();

        when(memberWritableRepository.save(any(Member.class))).thenReturn(savedMember);

        CqrsOutput<?> result = memberCommandUseCase.execute(input);

        assertNotNull(result);
        assertEquals(ExitCode.SUCCESS, result.getExitCode());
        assertEquals(1L, result.getData());

        verify(memberWritableRepository).save(any(Member.class));
        verify(eventBus).publishAsync(any());
    }

    @Test
    void testModifyMemberCommand() {
        Long memberId = 1L;
        String newEmail = "new.email@example.com";

        ModifyMemberEmailInputData input = ModifyMemberEmailInputData.builder()
                .id(memberId)
                .email(newEmail)
                .build();

        Member existingMember = Member.builder()
                .id(memberId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber(PhoneNumber.builder()
                        .countryCode("+886")
                        .number("123456789")
                        .build())
                .gender(Gender.MALE)
                .status(MemberStatus.ACTIVE)
                .createTime(Instant.now())
                .lastModifyTime(Instant.now())
                .deleted(0)
                .build();

        when(memberReadonlyRepository.findById(memberId)).thenReturn(Optional.of(existingMember));

        CqrsOutput<?> result = memberCommandUseCase.execute(input);

        assertNotNull(result);
        assertEquals(ExitCode.SUCCESS, result.getExitCode());
        assertEquals(memberId, result.getData());

        verify(memberReadonlyRepository).findById(memberId);
        verify(memberWritableRepository).modifyEmail(any(Member.class));
        verify(eventBus).publishAsync(any());
    }

    @Test
    void testModifyMemberCommandMemberNotFound() {
        Long memberId = 999L;

        ModifyMemberEmailInputData input = ModifyMemberEmailInputData.builder()
                .id(memberId)
                .email("new.email@example.com")
                .build();

        when(memberReadonlyRepository.findById(memberId)).thenReturn(Optional.empty());

        CqrsOutput<?> result = memberCommandUseCase.execute(input);

        assertNotNull(result);
        assertEquals(ExitCode.FAILURE, result.getExitCode());

        verify(memberReadonlyRepository).findById(memberId);
        verify(memberWritableRepository, never()).modifyEmail(any());
        verify(eventBus, never()).publishAsync(any());
    }

    @Test
    void testRemoveMemberCommand() {
        Long memberId = 1L;

        RemoveMemberInputData input = RemoveMemberInputData.builder()
                .id(memberId)
                .build();

        Member existingMember = Member.builder()
                .id(memberId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber(PhoneNumber.builder()
                        .countryCode("+886")
                        .number("123456789")
                        .build())
                .gender(Gender.MALE)
                .status(MemberStatus.ACTIVE)
                .createTime(Instant.now())
                .lastModifyTime(Instant.now())
                .deleted(0)
                .build();

        when(memberReadonlyRepository.findById(memberId)).thenReturn(Optional.of(existingMember));

        CqrsOutput<?> result = memberCommandUseCase.execute(input);

        assertNotNull(result);
        assertEquals(ExitCode.SUCCESS, result.getExitCode());
        assertEquals(memberId, result.getData());

        verify(memberReadonlyRepository).findById(memberId);
        verify(memberWritableRepository).markDeleted(any(Member.class));
        verify(eventBus).publishAsync(any());
    }

    @Test
    void testRemoveMemberCommandMemberNotFound() {
        Long memberId = 999L;

        RemoveMemberInputData input = RemoveMemberInputData.builder()
                .id(memberId)
                .build();

        when(memberReadonlyRepository.findById(memberId)).thenReturn(Optional.empty());

        CqrsOutput<?> result = memberCommandUseCase.execute(input);

        assertNotNull(result);
        assertEquals(ExitCode.FAILURE, result.getExitCode());

        verify(memberReadonlyRepository).findById(memberId);
        verify(memberWritableRepository, never()).markDeleted(any());
        verify(eventBus, never()).publishAsync(any());
    }

    @Test
    void testInvalidInput() {
        CqrsOutput<?> result = memberCommandUseCase.execute(null);

        assertNotNull(result);
        assertEquals(ExitCode.FAILURE, result.getExitCode());

        verify(memberWritableRepository, never()).save(any());
        verify(memberWritableRepository, never()).modifyEmail(any());
        verify(memberWritableRepository, never()).markDeleted(any());
        verify(eventBus, never()).publishAsync(any());
    }
}
