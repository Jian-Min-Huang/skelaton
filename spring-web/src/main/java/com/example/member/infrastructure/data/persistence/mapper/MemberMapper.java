package com.example.member.infrastructure.data.persistence.mapper;

import com.example.member.domain.entity.Member;
import com.example.member.domain.vo.PhoneNumber;
import com.example.member.domain.vo.enu.Gender;
import com.example.member.domain.vo.enu.MemberStatus;
import com.example.member.infrastructure.data.persistence.po.MemberPo;

public class MemberMapper {
    public static MemberPo toPo(final Member entity) {
        return MemberPo
                .builder()
                .id(entity.getId())
                .createdBy(entity.getCreatedBy())
                .lastModifiedBy(entity.getLastModifiedBy())
                .deletedBy(entity.getDeletedBy())
                .createTime(entity.getCreateTime())
                .lastModifyTime(entity.getLastModifyTime())
                .deleteTime(entity.getDeleteTime())
                .remark(entity.getRemark())
                .deleted(entity.getDeleted())
                .version(entity.getVersion())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber().getCountryCode() + entity.getPhoneNumber().getNumber())
                .gender(entity.getGender().getVal())
                .status(entity.getStatus().getVal())
                .build();
    }

    public static Member toModel(final MemberPo po) {
        return Member
                .builder()
                .id(po.getId())
                .createdBy(po.getCreatedBy())
                .lastModifiedBy(po.getLastModifiedBy())
                .deletedBy(po.getDeletedBy())
                .createTime(po.getCreateTime())
                .lastModifyTime(po.getLastModifyTime())
                .deleteTime(po.getDeleteTime())
                .remark(po.getRemark())
                .deleted(po.getDeleted())
                .version(po.getVersion())
                .firstName(po.getFirstName())
                .lastName(po.getLastName())
                .email(po.getEmail())
                .phoneNumber(PhoneNumber.fromRawString(po.getPhoneNumber()))
                .gender(Gender.fromVal(po.getGender()))
                .status(MemberStatus.fromVal(po.getStatus()))
                .build();
    }
}
