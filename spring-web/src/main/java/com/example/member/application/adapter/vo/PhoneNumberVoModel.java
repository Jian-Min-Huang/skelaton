package com.example.member.application.adapter.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberVoModel {
    private String countryCode;
    private String number;

    public static PhoneNumberVoModel fromRawString(final String phoneNumber) {
        return PhoneNumberVoModel
                .builder()
                .countryCode(phoneNumber.substring(0, 4))
                .number(phoneNumber.substring(4))
                .build();
    }
}
