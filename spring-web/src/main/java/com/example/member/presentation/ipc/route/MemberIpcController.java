package com.example.member.presentation.ipc.route;

import com.example.common.ddd.cqrs.CqrsOutput;
import com.example.common.ddd.cqrs.ExitCode;
import com.example.member.application.port.input.QueryMemberInputData;
import com.example.member.application.port.output.QueryMemberOutputData;
import com.example.member.application.usecase.query.MemberQueryUseCase;
import com.example.member.presentation.http.converter.MemberConverter;
import com.example.member.presentation.ipc.converter.MemberIpcConverter;
import com.example.member.presentation.ipc.protocol.MemberIpcProtocol;
import com.example.member.presentation.ipc.request.ModifyMemberStatusRequest;
import com.example.member.presentation.ipc.response.QueryMemberIpcResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MemberIpcController implements MemberIpcProtocol {
    private final MemberQueryUseCase memberQueryUseCase;

    @Override
    public QueryMemberIpcResponse queryMemberById(final Long id) {
        final QueryMemberInputData input = MemberConverter.toQueryMemberInput(id);
        final CqrsOutput<?> output = memberQueryUseCase.execute(input);

        if (output.getExitCode() == ExitCode.SUCCESS && output.getData() != null && output.getData() instanceof QueryMemberOutputData outputData) {
            return MemberIpcConverter.toQueryMemberIpcResponse(outputData);
        } else {
            return null;
        }
    }

    @Override
    public void modifyMemberStatus(final ModifyMemberStatusRequest modifyMemberStatusRequest) {
    }
}
