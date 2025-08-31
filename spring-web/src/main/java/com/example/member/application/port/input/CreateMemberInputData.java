package com.example.member.application.port.input;

import com.example.common.ddd.cqrs.CqrsInput;
import com.example.member.application.adapter.vo.PhoneNumberVoModel;
import com.example.member.application.adapter.vo.enu.GenderEnuModel;
import com.example.member.application.adapter.vo.enu.MemberStatusEnuModel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
public class CreateMemberInputData implements CqrsInput<Long> {
    private String firstName;
    private String lastName;
    private String email;
    private PhoneNumberVoModel phoneNumber;
    private GenderEnuModel gender;
    private MemberStatusEnuModel status;

    @Override
    public Long getId() {
        return 0L;
    }
}
