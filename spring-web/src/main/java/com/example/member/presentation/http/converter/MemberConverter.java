package com.example.member.presentation.http.converter;

import com.example.member.application.adapter.vo.PhoneNumberVoModel;
import com.example.member.application.adapter.vo.enu.GenderEnuModel;
import com.example.member.application.adapter.vo.enu.MemberStatusEnuModel;
import com.example.member.application.port.input.CreateMemberInputData;
import com.example.member.application.port.input.ModifyMemberEmailInputData;
import com.example.member.application.port.input.QueryMemberInputData;
import com.example.member.application.port.input.QueryMembersInputData;
import com.example.member.application.port.input.RemoveMemberInputData;
import com.example.member.application.port.output.QueryMemberOutputData;
import com.example.member.presentation.http.dto.PhoneNumberDto;
import com.example.member.presentation.http.dto.enu.GenderEnuDto;
import com.example.member.presentation.http.dto.enu.MemberStatusEnuDto;
import com.example.member.presentation.http.request.CreateMemberRequest;
import com.example.member.presentation.http.request.ModifyMemberEmailRequest;
import com.example.member.presentation.http.request.QueryMembersRequest;
import com.example.member.presentation.http.response.QueryMemberResponse;

public class MemberConverter {
    public static CreateMemberInputData toCreateMemberInput(final CreateMemberRequest request) {
        return CreateMemberInputData
                .builder()
                .id(null)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(toModel(request.getPhoneNumber()))
                .gender(toModel(request.getGender()))
                .build();
    }

    public static QueryMemberInputData toQueryMemberInput(final Long id) {
        return QueryMemberInputData
                .builder()
                .id(id)
                .build();
    }

    public static QueryMembersInputData toQueryMembersInput(final QueryMembersRequest request) {
        return QueryMembersInputData
                .builder()
                .registeredInXDays(request.getRegisteredInXDays())
                .statuses(request.getStatuses().stream().map(element -> MemberStatusEnuModel.valueOf(element.name())).toList())
                .pageNumber(request.getPageNumber())
                .pageSize(request.getPageSize())
                .build();
    }

    public static ModifyMemberEmailInputData toModifyMemberInput(final ModifyMemberEmailRequest request) {
        return ModifyMemberEmailInputData
                .builder()
                .id(request.getId())
                .email(request.getEmail())
                .build();
    }

    public static RemoveMemberInputData toRemoveMemberInput(final Long id) {
        return RemoveMemberInputData
                .builder()
                .id(id)
                .build();
    }

    public static QueryMemberResponse toQueryMemberResponse(final QueryMemberOutputData output) {
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

    private static PhoneNumberVoModel toModel(final PhoneNumberDto phoneNumber) {
        return PhoneNumberVoModel
                .builder()
                .countryCode(phoneNumber.getCountryCode())
                .number(phoneNumber.getNumber())
                .build();
    }

    private static GenderEnuModel toModel(final GenderEnuDto gender) {
        return GenderEnuModel.valueOf(gender.name());
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
