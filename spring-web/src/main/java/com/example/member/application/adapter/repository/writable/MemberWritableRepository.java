package com.example.member.application.adapter.repository.writable;

import com.example.common.ca.domain.WritableRepository;
import com.example.member.application.adapter.entity.MemberModel;

public interface MemberWritableRepository<T, ID> extends WritableRepository<T, ID> {
    void modifyEmail(final MemberModel data);

    void remove(final ID id);
}
