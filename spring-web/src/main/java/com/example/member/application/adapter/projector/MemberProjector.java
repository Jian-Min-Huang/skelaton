package com.example.member.application.adapter.projector;

import com.example.member.application.adapter.entity.MemberModel;
import com.example.member.application.adapter.vo.PhoneNumberVoModel;
import com.example.member.application.adapter.vo.enu.GenderEnuModel;
import com.example.member.application.adapter.vo.enu.MemberStatusEnuModel;
import com.example.member.application.usecase.port.input.CreateMemberInput;
import com.example.member.application.usecase.port.input.ModifyMemberEmailInput;

import java.time.Instant;

public class MemberProjector {
    public static MemberModel toMemberModel(final CreateMemberInput input) {
        return MemberModel
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
                .phoneNumber(PhoneNumberVoModel.builder().build())
                .gender(GenderEnuModel.valueOf(input.getGender()))
                .status(MemberStatusEnuModel.valueOf(null))
                .build();
    }

    public static MemberModel toMemberModel(final ModifyMemberEmailInput input) {
        return MemberModel
                .builder()
                .id(input.getId())
                .email(input.getEmail())
                .build();
    }
}
