package com.example.member.application.adapter.projector;

import com.example.common.data.Pagination;
import com.example.member.application.adapter.vo.PhoneNumberVoModel;
import com.example.member.application.adapter.vo.enu.GenderEnuModel;
import com.example.member.application.adapter.vo.enu.MemberStatusEnuModel;
import com.example.member.application.port.output.QueryMemberOutputData;
import com.example.member.domain.entity.Member;

public class MemberProjector {
//    public static Member toEntity(final CreateMemberInputData input) {
//        return Member
//                .builder()
//                .id(null)
//                .createdBy(null)
//                .lastModifiedBy(null)
//                .deletedBy(null)
//                .createTime(null)
//                .lastModifyTime(null)
//                .deleteTime(null)
//                .remark(null)
//                .deleted(0)
//                .firstName(input.getFirstName())
//                .lastName(input.getLastName())
//                .email(input.getEmail())
//                .phoneNumber(PhoneNumber.builder().countryCode(input.getPhoneNumber().getCountryCode()).number(input.getPhoneNumber().getNumber()).build())
//                .gender(Gender.valueOf(input.getGender().name()))
//                .status(MemberStatus.INACTIVE)
//                .build();
//    }

//    public static Member toEntity(final ModifyMemberEmailInputData input) {
//        return Member
//                .builder()
//                .id(input.getId())
//                .email(input.getEmail())
//                .build();
//    }

    public static QueryMemberOutputData toOutput(final Member entity) {
        return QueryMemberOutputData
                .builder()
                .id(entity.id)
//                .createTime(entity.getCreateTime())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .phoneNumber(PhoneNumberVoModel.builder().countryCode(entity.getPhoneNumber().getCountryCode()).number(entity.getPhoneNumber().getNumber()).build())
                .gender(GenderEnuModel.valueOf(entity.getGender().name()))
                .status(MemberStatusEnuModel.valueOf(entity.getStatus().name()))
                .build();
    }

    public static Pagination<QueryMemberOutputData> toOutput(final Pagination<Member> entities) {
        return Pagination
                .<QueryMemberOutputData>builder()
                .content(entities.getContent().stream().map(MemberProjector::toOutput).toList())
                .currentPage(entities.getCurrentPage())
                .pageSize(entities.getPageSize())
                .totalPages(entities.getTotalPages())
                .totalElements(entities.getTotalElements())
                .build();
    }
}
