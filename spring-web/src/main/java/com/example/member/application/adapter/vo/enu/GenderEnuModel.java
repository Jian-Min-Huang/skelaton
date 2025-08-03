package com.example.member.application.adapter.vo.enu;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GenderEnuModel {
    FEMALE(0),
    MALE(1),
    OTHER(2);

    private final Integer val;

    GenderEnuModel(final Integer val) {
        this.val = val;
    }

    public static GenderEnuModel fromVal(final Integer val) {
        return Arrays
                .stream(values())
                .filter(gender -> gender.val.equals(val))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("GenderEnuModel invalid val: " + val));
    }
}
