package com.example.member.presentation.http.route;

import com.example.common.data.Pagination;
import com.example.common.ddd.cqrs.CqrsOutput;
import com.example.common.ddd.cqrs.ExitCode;
import com.example.member.application.port.input.CreateMemberInputData;
import com.example.member.application.port.input.ModifyMemberEmailInputData;
import com.example.member.application.port.input.QueryMemberInputData;
import com.example.member.application.port.input.QueryMembersInputData;
import com.example.member.application.port.input.RemoveMemberInputData;
import com.example.member.application.port.output.QueryMemberOutputData;
import com.example.member.application.usecase.command.MemberCommandUseCase;
import com.example.member.application.usecase.query.MemberQueryUseCase;
import com.example.member.presentation.http.converter.MemberConverter;
import com.example.member.presentation.http.protocol.MemberProtocol;
import com.example.member.presentation.http.request.CreateMemberRequest;
import com.example.member.presentation.http.request.ModifyMemberEmailRequest;
import com.example.member.presentation.http.request.QueryMembersRequest;
import com.example.member.presentation.http.response.QueryMemberResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@Tag(name = "Member API", description = "APIs for managing members")
@RestController
@RequiredArgsConstructor
public class MemberController implements MemberProtocol {
    private final MemberCommandUseCase memberCommandUseCase;
    private final MemberQueryUseCase memberQueryUseCase;

    @Override
    public ResponseEntity<Void> createMember(final CreateMemberRequest request, final HttpServletRequest httpServletRequest) {
        final CreateMemberInputData input = MemberConverter.toCreateMemberInput(request);
        final CqrsOutput<?> output = memberCommandUseCase.execute(input);

        if (output.getExitCode() == ExitCode.SUCCESS && output.getData() != null && output.getData() instanceof Long id) {
            return ResponseEntity
                    .created(URI.create(httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + httpServletRequest.getContextPath() + "/api/v1/members/" + id))
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, output.getMessage());
        }
    }

    @Override
    public ResponseEntity<QueryMemberResponse> queryMemberById(final Long id) {
        final QueryMemberInputData input = MemberConverter.toQueryMemberInput(id);
        final CqrsOutput<?> output = memberQueryUseCase.execute(input);

        if (output.getExitCode() == ExitCode.SUCCESS && output.getData() != null && output.getData() instanceof QueryMemberOutputData outputData) {
            final QueryMemberResponse response = MemberConverter.toQueryMemberResponse(outputData);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Pagination<QueryMemberResponse>> queryMembers(final QueryMembersRequest request) {
        final QueryMembersInputData input = MemberConverter.toQueryMembersInput(request);
        final CqrsOutput<?> output = memberQueryUseCase.execute(input);

        if (output.getExitCode() == ExitCode.SUCCESS
                && output.getData() != null
                && output.getData() instanceof Pagination<?> pagination
                && pagination.getContent() != null
                && !pagination.getContent().isEmpty()
                && pagination.getContent().getFirst() instanceof QueryMemberOutputData) {
            final Pagination<QueryMemberResponse> response = Pagination.<QueryMemberResponse>builder()
                    .content(pagination.getContent().stream().map(element -> MemberConverter.toQueryMemberResponse((QueryMemberOutputData) element)).toList())
                    .currentPage(pagination.getCurrentPage())
                    .pageSize(pagination.getPageSize())
                    .totalPages(pagination.getTotalPages())
                    .totalElements(pagination.getTotalElements())
                    .build();

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.ok(Pagination.empty());
        }
    }

    @Override
    public ResponseEntity<Void> modifyMemberEmail(final ModifyMemberEmailRequest request) {
        final ModifyMemberEmailInputData input = MemberConverter.toModifyMemberInput(request);
        final CqrsOutput<?> output = memberCommandUseCase.execute(input);

        if (output.getExitCode() == ExitCode.SUCCESS) {
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, output.getMessage());
        }
    }

    @Override
    public ResponseEntity<Void> removeMemberById(final Long id) {
        final RemoveMemberInputData input = MemberConverter.toRemoveMemberInput(id);
        final CqrsOutput<?> output = memberCommandUseCase.execute(input);

        if (output.getExitCode() == ExitCode.SUCCESS) {
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, output.getMessage());
        }
    }
}
