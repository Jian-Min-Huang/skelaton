package com.example.skill.service.enu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Category {
    CUSTOM(1),
    THIRD_PARTY(2),
    ;

    private final int val;

    public static Category getByVal(int val) {
        for (Category category : values()) {
            if (category.getVal() == val) {
                return category;
            }
        }

        return null;
    }
}
