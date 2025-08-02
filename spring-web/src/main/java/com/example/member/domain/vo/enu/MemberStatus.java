package com.example.member.domain.vo.enu;

public enum MemberStatus {
    INACTIVE(0),
    SUSPENDED(10),
    ACTIVE(20),
    DELETED(30);

    private final Integer val;

    MemberStatus(final Integer val) {
        this.val = val;
    }

    public Integer getVal() {
        return val;
    }
}
