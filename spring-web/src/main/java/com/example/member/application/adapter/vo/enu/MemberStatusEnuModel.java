package com.example.member.application.adapter.vo.enu;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MemberStatusEnuModel {
    INACTIVE(0),
    SUSPENDED(10),
    ACTIVE(20),
    DELETED(30);

    private final Integer val;

    MemberStatusEnuModel(final Integer val) {
        this.val = val;
    }

    public static MemberStatusEnuModel fromVal(final Integer val) {
        return Arrays
                .stream(values())
                .filter(status -> status.val.equals(val))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("MemberStatusEnuModel invalid val: " + val));
    }
}
