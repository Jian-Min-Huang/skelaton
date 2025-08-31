package com.example.member.domain.vo.enu;

import com.example.common.ddd.domain.ValueObject;

public enum MemberStatus implements ValueObject {
    INACTIVE,
    SUSPENDED,
    ACTIVE,
    DELETED
}
