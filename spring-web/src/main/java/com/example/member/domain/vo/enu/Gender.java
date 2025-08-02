package com.example.member.domain.vo.enu;

public enum Gender {
    FEMALE(0),
    MALE(1),
    OTHER(2);

    private final Integer val;

    Gender(final Integer val) {
        this.val = val;
    }

    public Integer getVal() {
        return val;
    }
}
