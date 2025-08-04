package com.example.member.presentation.web.route;

import com.example.common.ca.cqrs.CqrsOutput;
import com.example.common.ca.cqrs.ExitCode;
import com.example.common.data.Pagination;
import com.example.member.application.port.input.CreateMemberInput;
import com.example.member.application.port.input.ModifyMemberEmailInput;
import com.example.member.application.port.input.QueryMemberInput;
import com.example.member.application.port.input.QueryMembersInput;
import com.example.member.application.port.input.RemoveMemberInput;
import com.example.member.application.port.output.QueryMemberOutputData;
import com.example.member.application.usecase.command.MemberCommandUseCase;
import com.example.member.application.usecase.query.MemberQueryUseCase;
import com.example.member.presentation.web.converter.MemberConverter;
import com.example.member.presentation.web.protocol.MemberProtocol;
import com.example.member.presentation.web.request.CreateMemberRequest;
import com.example.member.presentation.web.request.ModifyMemberEmailRequest;
import com.example.member.presentation.web.request.QueryMembersRequest;
import com.example.member.presentation.web.response.QueryMemberResponse;
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
        final CreateMemberInput input = MemberConverter.toCreateMemberInput(request);
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
        final QueryMemberInput input = MemberConverter.toQueryMemberInput(id);
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
        final QueryMembersInput input = MemberConverter.toQueryMembersInput(request);
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
        final ModifyMemberEmailInput input = MemberConverter.toModifyMemberInput(request);
        final CqrsOutput<?> output = memberCommandUseCase.execute(input);

        if (output.getExitCode() == ExitCode.SUCCESS) {
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, output.getMessage());
        }
    }

    @Override
    public ResponseEntity<Void> removeMemberById(final Long id) {
        final RemoveMemberInput input = MemberConverter.toRemoveMemberInput(id);
        final CqrsOutput<?> output = memberCommandUseCase.execute(input);

        if (output.getExitCode() == ExitCode.SUCCESS) {
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, output.getMessage());
        }
    }
}
