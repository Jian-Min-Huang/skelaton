package com.example.member.service.presentation.web.response;

import com.example.member.service.presentation.web.dto.PhoneNumberDto;
import com.example.member.service.presentation.web.dto.enu.GenderEnuDto;
import com.example.member.service.presentation.web.dto.enu.MemberStatusEnuDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryMemberResponse {
    private Long id;
    private Instant createTime;
    private String firstName;
    private String lastName;
    private String email;
    private PhoneNumberDto phoneNumber;
    private GenderEnuDto gender;
    private MemberStatusEnuDto status;
}
