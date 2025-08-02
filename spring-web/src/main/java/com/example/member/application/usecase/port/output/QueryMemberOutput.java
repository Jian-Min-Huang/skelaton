package com.example.member.application.usecase.port.output;

import com.example.member.application.adapter.vo.PhoneNumberVoModel;
import com.example.member.application.adapter.vo.enu.GenderEnuModel;
import com.example.member.application.adapter.vo.enu.MemberStatusEnuModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryMemberOutput {
    private Long id;
    private Instant createTime;
    private String firstName;
    private String lastName;
    private String email;
    private PhoneNumberVoModel phoneNumber;
    private GenderEnuModel gender;
    private MemberStatusEnuModel status;
}
