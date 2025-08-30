package com.example.member.presentation.ipc.converter;

import com.example.member.application.adapter.vo.PhoneNumberVoModel;
import com.example.member.application.adapter.vo.enu.GenderEnuModel;
import com.example.member.application.adapter.vo.enu.MemberStatusEnuModel;
import com.example.member.application.port.output.QueryMemberOutputData;
import com.example.member.presentation.ipc.dto.PhoneNumberIpcDto;
import com.example.member.presentation.ipc.dto.enu.GenderIpcEnuDto;
import com.example.member.presentation.ipc.dto.enu.MemberStatusIpcEnuDto;
import com.example.member.presentation.ipc.response.QueryMemberIpcResponse;

public class MemberIpcConverter {
    public static QueryMemberIpcResponse toQueryMemberIpcResponse(final QueryMemberOutputData output) {
        return QueryMemberIpcResponse
                .builder()
                .id(output.getId())
                .createTime(output.getCreateTime())
                .firstName(output.getFirstName())
                .lastName(output.getLastName())
                .email(output.getEmail())
                .phoneNumber(toDto(output.getPhoneNumber()))
                .gender(toDto(output.getGender()))
                .status(toDto(output.getStatus()))
                .build();
    }

    private static PhoneNumberIpcDto toDto(final PhoneNumberVoModel voModel) {
        return PhoneNumberIpcDto
                .builder()
                .countryCode(voModel.getCountryCode())
                .number(voModel.getNumber())
                .build();
    }

    private static GenderIpcEnuDto toDto(final GenderEnuModel enuModel) {
        return GenderIpcEnuDto.valueOf(enuModel.name());
    }

    private static MemberStatusIpcEnuDto toDto(final MemberStatusEnuModel enuModel) {
        return MemberStatusIpcEnuDto.valueOf(enuModel.name());
    }
}
