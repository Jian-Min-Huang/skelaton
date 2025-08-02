package com.example.member.service.presentation.web.converter;

import com.example.member.application.adapter.vo.PhoneNumberVoModel;
import com.example.member.application.adapter.vo.enu.GenderEnuModel;
import com.example.member.application.adapter.vo.enu.MemberStatusEnuModel;
import com.example.member.application.usecase.port.input.CreateMemberInput;
import com.example.member.application.usecase.port.input.ModifyMemberEmailInput;
import com.example.member.application.usecase.port.input.QueryMemberInput;
import com.example.member.application.usecase.port.input.QueryMembersInput;
import com.example.member.application.usecase.port.input.RemoveMemberInput;
import com.example.member.application.usecase.port.output.QueryMemberOutput;
import com.example.member.service.presentation.web.dto.PhoneNumberDto;
import com.example.member.service.presentation.web.dto.enu.GenderEnuDto;
import com.example.member.service.presentation.web.dto.enu.MemberStatusEnuDto;
import com.example.member.service.presentation.web.request.CreateMemberRequest;
import com.example.member.service.presentation.web.request.ModifyMemberEmailRequest;
import com.example.member.service.presentation.web.request.QueryMembersRequest;
import com.example.member.service.presentation.web.response.CreateMemberResponse;
import com.example.member.service.presentation.web.response.QueryMemberResponse;

public class MemberConverter {
    public static CreateMemberInput toCreateMemberInput(final CreateMemberRequest request) {
        return CreateMemberInput
                .builder()
                .id(null)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender())
                .build();
    }

    public static QueryMemberInput toQueryMemberInput(final Long id) {
        return QueryMemberInput
                .builder()
                .id(id)
                .build();
    }

    public static QueryMembersInput toQueryMembersInput(final QueryMembersRequest request) {
        return QueryMembersInput
                .builder()
                .registeredInXDays(request.getRegisteredInXDays())
                .statusList(request.getStatusList())
                .pageNumber(0)
                .pageSize(0)
                .build();
    }

    public static ModifyMemberEmailInput toModifyMemberInput(final ModifyMemberEmailRequest request) {
        return ModifyMemberEmailInput
                .builder()
                .id(request.getId())
                .email(request.getEmail())
                .build();
    }

    public static RemoveMemberInput toRemoveMemberInput(final Long id) {
        return RemoveMemberInput
                .builder()
                .id(id)
                .build();
    }

    public static CreateMemberResponse toCreateMemberResponse(final Long id) {
        return CreateMemberResponse.builder().id(id).build();
    }

    public static QueryMemberResponse toQueryMemberResponse(final QueryMemberOutput output) {
        return QueryMemberResponse
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

    private static PhoneNumberDto toDto(final PhoneNumberVoModel voModel) {
        return PhoneNumberDto
                .builder()
                .countryCode(voModel.getCountryCode())
                .number(voModel.getNumber())
                .build();
    }

    private static GenderEnuDto toDto(final GenderEnuModel enuModel) {
        return GenderEnuDto.valueOf(enuModel.name());
    }

    private static MemberStatusEnuDto toDto(final MemberStatusEnuModel enuModel) {
        return MemberStatusEnuDto.valueOf(enuModel.name());
    }
}
