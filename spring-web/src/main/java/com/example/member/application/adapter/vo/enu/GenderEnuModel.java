package com.example.member.application.adapter.vo.enu;

public enum GenderEnuModel {
    FEMALE(0),
    MALE(1),
    OTHER(2);

    private final Integer val;

    GenderEnuModel(final Integer val) {
        this.val = val;
    }

    public Integer getVal() {
        return val;
    }
}
