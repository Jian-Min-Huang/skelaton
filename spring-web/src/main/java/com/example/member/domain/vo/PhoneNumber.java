package com.example.member.domain.vo;

import com.example.common.ddd.domain.ValueObject;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
public class PhoneNumber implements ValueObject {
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
