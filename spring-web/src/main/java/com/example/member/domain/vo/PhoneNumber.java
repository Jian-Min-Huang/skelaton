package com.example.member.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumber {
    private String countryCode;
    private String number;

    public static PhoneNumber fromRawString(final String phoneNumber) {
        return PhoneNumber
                .builder()
                .countryCode(phoneNumber.substring(0, 4))
                .number(phoneNumber.substring(4))
                .build();
    }

    public String toRawString() {
        return this.countryCode + this.number;
    }
}
