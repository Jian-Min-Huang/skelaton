package com.example.member.domain.vo.enu;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Gender {
    FEMALE(0),
    MALE(1),
    OTHER(2);

    private final Integer val;

    Gender(final Integer val) {
        this.val = val;
    }

    public static Gender fromVal(final Integer val) {
        return Arrays
                .stream(values())
                .filter(gender -> gender.val.equals(val))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Gender invalid val: " + val));
    }
}
