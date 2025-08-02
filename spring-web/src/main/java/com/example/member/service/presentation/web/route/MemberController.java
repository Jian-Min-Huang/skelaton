package com.example.member.service.presentation.web.route;

import com.example.common.ca.cqrs.CqrsOutput;
import com.example.common.ca.cqrs.ExitCode;
import com.example.common.data.Pagination;
import com.example.member.application.usecase.command.MemberCommandUseCase;
import com.example.member.application.usecase.port.input.CreateMemberInput;
import com.example.member.application.usecase.port.input.ModifyMemberEmailInput;
import com.example.member.application.usecase.port.input.QueryMemberInput;
import com.example.member.application.usecase.port.input.QueryMembersInput;
import com.example.member.application.usecase.port.input.RemoveMemberInput;
import com.example.member.application.usecase.port.output.QueryMemberOutput;
import com.example.member.application.usecase.query.MemberQueryUseCase;
import com.example.member.service.presentation.web.converter.MemberConverter;
import com.example.member.service.presentation.web.protocol.MemberProtocol;
import com.example.member.service.presentation.web.request.CreateMemberRequest;
import com.example.member.service.presentation.web.request.ModifyMemberEmailRequest;
import com.example.member.service.presentation.web.request.QueryMembersRequest;
import com.example.member.service.presentation.web.response.CreateMemberResponse;
import com.example.member.service.presentation.web.response.QueryMemberResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<CreateMemberResponse> createMember(final CreateMemberRequest request) {
        final CreateMemberInput input = MemberConverter.toCreateMemberInput(request);
        final CqrsOutput<?> output = memberCommandUseCase.execute(input);

        if (output.getExitCode() == ExitCode.SUCCESS && output.getData() != null) {
            final CreateMemberResponse response = MemberConverter.toCreateMemberResponse((Long) output.getData());

            return ResponseEntity.created(URI.create("/api/v1/members/" + response.getId())).body(response);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, output.getMessage());
        }
    }

    @Override
    public ResponseEntity<QueryMemberResponse> getMemberById(final Long id) {
        final QueryMemberInput input = MemberConverter.toQueryMemberInput(id);
        final CqrsOutput<?> output = memberQueryUseCase.execute(input);

        if (output.getExitCode() == ExitCode.SUCCESS && output.getData() != null) {
            final QueryMemberResponse response = MemberConverter.toQueryMemberResponse((QueryMemberOutput) output.getData());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Pagination<QueryMemberResponse>> getMembers(final QueryMembersRequest request) {
        final QueryMembersInput input = MemberConverter.toQueryMembersInput(request);
        final CqrsOutput<?> output = memberQueryUseCase.execute(input);

        if (output.getExitCode() == ExitCode.SUCCESS && output.getData() != null) {
            return ResponseEntity.ok(Pagination.empty());
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
