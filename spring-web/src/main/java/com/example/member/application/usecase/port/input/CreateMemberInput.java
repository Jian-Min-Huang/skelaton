package com.example.member.application.usecase.port.input;

import com.example.common.ca.cqrs.CqrsInput;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateMemberInput extends CqrsInput<Long> {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Integer gender;
}
