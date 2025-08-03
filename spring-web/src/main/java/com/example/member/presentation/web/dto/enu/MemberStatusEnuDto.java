package com.example.member.presentation.web.dto.enu;

import lombok.Getter;

@Getter
public enum MemberStatusEnuDto {
    INACTIVE(0),
    SUSPENDED(10),
    ACTIVE(20),
    DELETED(30);

    private final Integer val;

    MemberStatusEnuDto(final Integer val) {
        this.val = val;
    }
}
