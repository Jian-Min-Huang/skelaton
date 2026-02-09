package com.example.preference.service.enu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AppearanceMode {
    LIGHT(1),
    DARK(2),
    SYSTEM(3),
    ;

    private final int val;

    public static AppearanceMode getByVal(int val) {
        for (AppearanceMode mode : values()) {
            if (mode.getVal() == val) {
                return mode;
            }
        }

        return null;
    }
}
