package com.example.member.service.infrastructure.data.persistence.mapper;

import com.example.member.application.adapter.entity.MemberModel;
import com.example.member.application.adapter.vo.PhoneNumberVoModel;
import com.example.member.application.adapter.vo.enu.GenderEnuModel;
import com.example.member.application.adapter.vo.enu.MemberStatusEnuModel;
import com.example.member.service.infrastructure.data.persistence.po.MemberPo;

public class MemberMapper {
    public static MemberPo toPo(final MemberModel memberModel) {
        return MemberPo
                .builder()
                .id(memberModel.getId())
                .createdBy(memberModel.getCreatedBy())
                .lastModifiedBy(memberModel.getLastModifiedBy())
                .deletedBy(memberModel.getDeletedBy())
                .createTime(memberModel.getCreateTime())
                .lastModifyTime(memberModel.getLastModifyTime())
                .deleteTime(memberModel.getDeleteTime())
                .remark(memberModel.getRemark())
                .deleted(memberModel.getDeleted())
                .version(memberModel.getVersion())
                .firstName(memberModel.getFirstName())
                .lastName(memberModel.getLastName())
                .email(memberModel.getEmail())
                .phoneNumber(memberModel.getPhoneNumber().getCountryCode() + memberModel.getPhoneNumber().getNumber())
                .gender(memberModel.getGender().getVal())
                .status(memberModel.getStatus().getVal())
                .build();
    }

    public static MemberModel toModel(final MemberPo memberPo) {
        return MemberModel
                .builder()
                .id(memberPo.getId())
                .createdBy(memberPo.getCreatedBy())
                .lastModifiedBy(memberPo.getLastModifiedBy())
                .deletedBy(memberPo.getDeletedBy())
                .createTime(memberPo.getCreateTime())
                .lastModifyTime(memberPo.getLastModifyTime())
                .deleteTime(memberPo.getDeleteTime())
                .remark(memberPo.getRemark())
                .deleted(memberPo.getDeleted())
                .version(memberPo.getVersion())
                .firstName(memberPo.getFirstName())
                .lastName(memberPo.getLastName())
                .email(memberPo.getEmail())
                .phoneNumber(PhoneNumberVoModel.fromRawString(memberPo.getPhoneNumber()))
                .gender(GenderEnuModel.fromVal(memberPo.getGender()))
                .status(MemberStatusEnuModel.fromVal(memberPo.getStatus()))
                .build();
    }
}
