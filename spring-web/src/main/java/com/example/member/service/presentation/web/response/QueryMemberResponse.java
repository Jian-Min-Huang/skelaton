package com.example.member.service.presentation.web.response;

import com.example.member.service.presentation.web.dto.PhoneNumberDto;
import com.example.member.service.presentation.web.dto.enu.GenderEnuDto;
import com.example.member.service.presentation.web.dto.enu.MemberStatusEnuDto;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "1")
    private Long id;
    @Schema(example = "2023-10-01T12:00:00Z")
    private Instant createTime;
    @Schema(example = "Vincent")
    private String firstName;
    @Schema(example = "Huang")
    private String lastName;
    @Schema(example = "vincent.huang@example.com")
    private String email;
    private PhoneNumberDto phoneNumber;
    private GenderEnuDto gender;
    private MemberStatusEnuDto status;
}
