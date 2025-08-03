package com.example.member.domain.vo.enu;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MemberStatus {
    INACTIVE(0),
    SUSPENDED(10),
    ACTIVE(20),
    DELETED(30);

    private final Integer val;

    MemberStatus(final Integer val) {
        this.val = val;
    }

    public static MemberStatus fromVal(final Integer val) {
        return Arrays
                .stream(values())
                .filter(status -> status.val.equals(val))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("MemberStatus invalid val: " + val));
    }
}
