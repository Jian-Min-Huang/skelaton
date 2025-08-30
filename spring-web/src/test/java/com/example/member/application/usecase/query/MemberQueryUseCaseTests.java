package com.example.member.application.usecase.query;

import com.example.common.ca.EventBus;
import com.example.common.ca.cqrs.CqrsOutput;
import com.example.common.ca.cqrs.ExitCode;
import com.example.common.data.Pagination;
import com.example.member.application.adapter.vo.enu.MemberStatusEnuModel;
import com.example.member.application.port.input.QueryMemberInputData;
import com.example.member.application.port.input.QueryMembersInputData;
import com.example.member.application.port.output.QueryMemberOutputData;
import com.example.member.domain.entity.Member;
import com.example.member.domain.repository.readonly.MemberReadonlyRepository;
import com.example.member.domain.vo.PhoneNumber;
import com.example.member.domain.vo.enu.Gender;
import com.example.member.domain.vo.enu.MemberStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberQueryUseCaseTests {

    @Mock
    private EventBus eventBus;

    @Mock
    private MemberReadonlyRepository<Member, Long> memberReadonlyRepository;

    private MemberQueryUseCase memberQueryUseCase;

    @BeforeEach
    void setUp() {
        memberQueryUseCase = new MemberQueryUseCase(eventBus, memberReadonlyRepository);
    }

    @Test
    void testQueryMemberSuccess() {
        Long memberId = 1L;
        QueryMemberInputData input = QueryMemberInputData.builder()
            .id(memberId)
            .build();

        Member member = Member.builder()
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

        when(memberReadonlyRepository.findById(memberId)).thenReturn(Optional.of(member));

        CqrsOutput<?> result = memberQueryUseCase.execute(input);

        assertNotNull(result);
        assertEquals(ExitCode.SUCCESS, result.getExitCode());
        assertTrue(result.getData() instanceof QueryMemberOutputData);

        QueryMemberOutputData outputData = (QueryMemberOutputData) result.getData();
        assertEquals(memberId, outputData.getId());
        assertEquals("John", outputData.getFirstName());
        assertEquals("Doe", outputData.getLastName());
        assertEquals("john.doe@example.com", outputData.getEmail());

        verify(memberReadonlyRepository).findById(memberId);
        verify(eventBus).publishAsync(any());
    }

    @Test
    void testQueryMemberNotFound() {
        Long memberId = 999L;
        QueryMemberInputData input = QueryMemberInputData.builder()
            .id(memberId)
            .build();

        when(memberReadonlyRepository.findById(memberId)).thenReturn(Optional.empty());

        CqrsOutput<?> result = memberQueryUseCase.execute(input);

        assertNotNull(result);
        assertEquals(ExitCode.FAILURE, result.getExitCode());

        verify(memberReadonlyRepository).findById(memberId);
        verify(eventBus, never()).publishAsync(any());
    }

    @Test
    void testQueryMembersSuccess() {
        QueryMembersInputData input = QueryMembersInputData.builder()
            .registeredInXDays(30)
            .statuses(List.of(MemberStatusEnuModel.ACTIVE))
            .pageNumber(0)
            .pageSize(10)
            .build();

        Member member1 = Member.builder()
            .id(1L)
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

        Member member2 = Member.builder()
            .id(2L)
            .firstName("Jane")
            .lastName("Smith")
            .email("jane.smith@example.com")
            .phoneNumber(PhoneNumber.builder()
                .countryCode("+886")
                .number("987654321")
                .build())
            .gender(Gender.FEMALE)
            .status(MemberStatus.ACTIVE)
            .createTime(Instant.now())
            .lastModifyTime(Instant.now())
            .deleted(0)
            .build();

        Pagination<Member> memberPagination = Pagination.<Member>builder()
            .content(List.of(member1, member2))
            .currentPage(0)
            .pageSize(10)
            .totalPages(1)
            .totalElements(2L)
            .build();

        when(memberReadonlyRepository.findAll(
            eq(30),
            eq(List.of(MemberStatus.ACTIVE)),
            eq(0),
            eq(10)
        )).thenReturn(memberPagination);

        CqrsOutput<?> result = memberQueryUseCase.execute(input);

        assertNotNull(result);
        assertEquals(ExitCode.SUCCESS, result.getExitCode());
        assertTrue(result.getData() instanceof Pagination);

        @SuppressWarnings("unchecked")
        Pagination<QueryMemberOutputData> outputPagination = (Pagination<QueryMemberOutputData>) result.getData();
        assertEquals(2, outputPagination.getContent().size());
        assertEquals(0, outputPagination.getCurrentPage());
        assertEquals(10, outputPagination.getPageSize());
        assertEquals(1, outputPagination.getTotalPages());
        assertEquals(2L, outputPagination.getTotalElements());

        verify(memberReadonlyRepository).findAll(30, List.of(MemberStatus.ACTIVE), 0, 10);
        verify(eventBus).publishAsync(any());
    }

    @Test
    void testQueryMembersWithMultipleStatuses() {
        QueryMembersInputData input = QueryMembersInputData.builder()
            .registeredInXDays(7)
            .statuses(List.of(MemberStatusEnuModel.ACTIVE, MemberStatusEnuModel.INACTIVE))
            .pageNumber(1)
            .pageSize(5)
            .build();

        Pagination<Member> emptyPagination = Pagination.<Member>builder()
            .content(List.of())
            .currentPage(1)
            .pageSize(5)
            .totalPages(0)
            .totalElements(0L)
            .build();

        when(memberReadonlyRepository.findAll(
            eq(7),
            eq(List.of(MemberStatus.ACTIVE, MemberStatus.INACTIVE)),
            eq(1),
            eq(5)
        )).thenReturn(emptyPagination);

        CqrsOutput<?> result = memberQueryUseCase.execute(input);

        assertNotNull(result);
        assertEquals(ExitCode.SUCCESS, result.getExitCode());
        assertTrue(result.getData() instanceof Pagination);

        @SuppressWarnings("unchecked")
        Pagination<QueryMemberOutputData> outputPagination = (Pagination<QueryMemberOutputData>) result.getData();
        assertEquals(0, outputPagination.getContent().size());

        verify(memberReadonlyRepository).findAll(7, List.of(MemberStatus.ACTIVE, MemberStatus.INACTIVE), 1, 5);
        verify(eventBus).publishAsync(any());
    }

    @Test
    void testInvalidInput() {
        CqrsOutput<?> result = memberQueryUseCase.execute(null);

        assertNotNull(result);
        assertEquals(ExitCode.FAILURE, result.getExitCode());
        assertNotNull(result.getMessage());

        verify(memberReadonlyRepository, never()).findById(any());
        verify(memberReadonlyRepository, never()).findAll(any(), any(), any(), any());
        verify(eventBus, never()).publishAsync(any());
    }

//    @Test
//    void testUnknownInputType() {
//        CqrsOutput<?> result = memberQueryUseCase.execute(MockInput.builder().id(1L).build());
//
//        assertNotNull(result);
//        assertEquals(ExitCode.FAILURE, result.getExitCode());
//        assertTrue(result.getMessage().contains("Invalid Input"));
//
//        verify(memberReadonlyRepository, never()).findById(any());
//        verify(memberReadonlyRepository, never()).findAll(any(), any(), any(), any());
//        verify(eventBus, never()).publishAsync(any());
//    }

    @Test
    void testQueryMemberWithException() {
        Long memberId = 1L;
        QueryMemberInputData input = QueryMemberInputData.builder()
            .id(memberId)
            .build();

        when(memberReadonlyRepository.findById(memberId))
            .thenThrow(new RuntimeException("Database connection error"));

        CqrsOutput<?> result = memberQueryUseCase.execute(input);

        assertNotNull(result);
        assertEquals(ExitCode.FAILURE, result.getExitCode());
        assertEquals("Database connection error", result.getMessage());

        verify(memberReadonlyRepository).findById(memberId);
        verify(eventBus, never()).publishAsync(any());
    }

    @Test
    void testQueryMembersWithException() {
        QueryMembersInputData input = QueryMembersInputData.builder()
            .registeredInXDays(30)
            .statuses(List.of(MemberStatusEnuModel.ACTIVE))
            .pageNumber(0)
            .pageSize(10)
            .build();

        when(memberReadonlyRepository.findAll(any(), any(), any(), any()))
            .thenThrow(new RuntimeException("Service unavailable"));

        CqrsOutput<?> result = memberQueryUseCase.execute(input);

        assertNotNull(result);
        assertEquals(ExitCode.FAILURE, result.getExitCode());
        assertEquals("Service unavailable", result.getMessage());

        verify(memberReadonlyRepository).findAll(30, List.of(MemberStatus.ACTIVE), 0, 10);
        verify(eventBus, never()).publishAsync(any());
    }
}
