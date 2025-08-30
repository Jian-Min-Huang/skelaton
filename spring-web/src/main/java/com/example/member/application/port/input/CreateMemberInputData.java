package com.example.member.application.port.input;

import com.example.common.ca.cqrs.CqrsInput;
import com.example.member.application.adapter.vo.PhoneNumberVoModel;
import com.example.member.application.adapter.vo.enu.GenderEnuModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateMemberInputData extends CqrsInput<Long> {
    private String firstName;
    private String lastName;
    private String email;
    private PhoneNumberVoModel phoneNumber;
    private GenderEnuModel gender;
}
