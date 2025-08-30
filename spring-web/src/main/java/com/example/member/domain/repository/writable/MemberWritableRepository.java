package com.example.member.domain.repository.writable;

import com.example.common.ca.domain.WritableRepository;
import com.example.member.domain.entity.Member;

public interface MemberWritableRepository<T, ID> extends WritableRepository<T, ID> {
    void modifyEmail(final Member entity);
}
