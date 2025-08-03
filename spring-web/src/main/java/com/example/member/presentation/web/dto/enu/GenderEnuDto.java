package com.example.member.presentation.web.dto.enu;

import lombok.Getter;

@Getter
public enum GenderEnuDto {
    FEMALE(0),
    MALE(1),
    OTHER(2);

    private final Integer val;

    GenderEnuDto(final Integer val) {
        this.val = val;
    }
}
