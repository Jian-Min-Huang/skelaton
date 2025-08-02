package com.example.member.application.adapter.vo.enu;

public enum MemberStatusEnuModel {
    INACTIVE(0),
    SUSPENDED(10),
    ACTIVE(20),
    DELETED(30);

    private final Integer val;

    MemberStatusEnuModel(final Integer val) {
        this.val = val;
    }

    public Integer getVal() {
        return val;
    }
}
