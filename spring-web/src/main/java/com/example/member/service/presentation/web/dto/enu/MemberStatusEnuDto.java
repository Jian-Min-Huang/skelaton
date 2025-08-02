package com.example.member.service.presentation.web.dto.enu;

public enum MemberStatusEnuDto {
    INACTIVE(0),
    SUSPENDED(10),
    ACTIVE(20),
    DELETED(30);

    private final Integer val;

    MemberStatusEnuDto(final Integer val) {
        this.val = val;
    }

    public Integer getVal() {
        return val;
    }
}
