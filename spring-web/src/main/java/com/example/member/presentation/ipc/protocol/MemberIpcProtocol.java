package com.example.member.presentation.ipc.protocol;

import com.example.member.presentation.ipc.response.QueryMemberIpcResponse;

public interface MemberIpcProtocol {
    QueryMemberIpcResponse queryMemberById(final Long id);
}
