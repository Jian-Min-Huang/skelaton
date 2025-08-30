package com.example.member.application.port.output;

import com.example.member.application.adapter.vo.PhoneNumberVoModel;
import com.example.member.application.adapter.vo.enu.GenderEnuModel;
import com.example.member.application.adapter.vo.enu.MemberStatusEnuModel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
public class QueryMemberOutputData {
    private Long id;
    private Instant createTime;
    private String firstName;
    private String lastName;
    private String email;
    private PhoneNumberVoModel phoneNumber;
    private GenderEnuModel gender;
    private MemberStatusEnuModel status;
}
