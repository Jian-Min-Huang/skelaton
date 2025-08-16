package com.example.member.presentation.ipc.facade;

import com.example.member.presentation.ipc.response.QueryIpcMemberResponse;

public interface MemberIpcProtocol {
    QueryIpcMemberResponse queryMemberById(final Long id);
}
