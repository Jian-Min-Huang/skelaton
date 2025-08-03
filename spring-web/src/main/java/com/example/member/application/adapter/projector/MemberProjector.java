package com.example.member.application.adapter.projector;

import com.example.common.data.Pagination;
import com.example.member.application.port.input.CreateMemberInput;
import com.example.member.application.port.input.ModifyMemberEmailInput;
import com.example.member.application.port.output.QueryMemberOutputData;
import com.example.member.domain.entity.Member;
import com.example.member.domain.vo.PhoneNumber;
import com.example.member.domain.vo.enu.Gender;
import com.example.member.domain.vo.enu.MemberStatus;

import java.time.Instant;

public class MemberProjector {
    public static Member toEntity(final CreateMemberInput input) {
        return Member
                .builder()
                .id(null)
                .createdBy(null)
                .lastModifiedBy(null)
                .deletedBy(null)
                .createTime(Instant.now())
                .lastModifyTime(null)
                .deleteTime(null)
                .remark(null)
                .deleted(0)
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .email(input.getEmail())
                .phoneNumber(PhoneNumber.builder().countryCode(input.getPhoneNumber().getCountryCode()).number(input.getPhoneNumber().getNumber()).build())
                .gender(Gender.fromVal(input.getGender().getVal()))
                .status(MemberStatus.INACTIVE)
                .build();
    }

    public static Member toEntity(final ModifyMemberEmailInput input) {
        return Member
                .builder()
                .id(input.getId())
                .email(input.getEmail())
                .build();
    }

    public static QueryMemberOutputData toOutput(final Member enitty) {
        return null;
    }

    public static Pagination<QueryMemberOutputData> toOutput(final Pagination<Member> enitties) {
        return null;
    }
}
