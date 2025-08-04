package com.example.member.infrastructure.persistence.mapper;

import com.example.member.domain.entity.Member;
import com.example.member.domain.vo.PhoneNumber;
import com.example.member.infrastructure.persistence.po.MemberPo;

import java.time.Instant;

public class MemberMapper {
    public static MemberPo toNewPo(final Member entity) {
        return MemberPo
                .builder()
                .id(entity.getId())
                .createdBy(entity.getCreatedBy())
                .lastModifiedBy(null)
                .deletedBy(null)
                .createTime(Instant.now())
                .lastModifyTime(null)
                .deleteTime(null)
                .remark(entity.getRemark())
                .deleted(0)
                .version(null)
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber().getCountryCode() + entity.getPhoneNumber().getNumber())
                .gender(entity.getGender())
                .status(entity.getStatus())
                .build();
    }

    public static Member toEntity(final MemberPo po) {
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
                .gender(po.getGender())
                .status(po.getStatus())
                .build();
    }
}
