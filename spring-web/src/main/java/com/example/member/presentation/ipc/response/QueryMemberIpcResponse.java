package com.example.member.presentation.ipc.response;

import com.example.member.presentation.ipc.dto.PhoneNumberIpcDto;
import com.example.member.presentation.ipc.dto.enu.GenderIpcEnuDto;
import com.example.member.presentation.ipc.dto.enu.MemberStatusIpcEnuDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryMemberIpcResponse {
    private Long id;
    private Instant createTime;
    private String firstName;
    private String lastName;
    private String email;
    private PhoneNumberIpcDto phoneNumber;
    private GenderIpcEnuDto gender;
    private MemberStatusIpcEnuDto status;
}