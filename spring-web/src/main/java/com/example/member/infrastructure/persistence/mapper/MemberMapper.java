package com.example.member.infrastructure.persistence.mapper;

import com.example.member.domain.entity.Member;
import com.example.member.domain.vo.PhoneNumber;
import com.example.member.infrastructure.persistence.po.MemberPo;

public class MemberMapper {
    public static MemberPo toNewPo(final Member entity) {
        return MemberPo
                .builder()
                .id(entity.id)
                .createdBy(entity.createdBy)
                .lastModifiedBy(entity.lastModifiedBy)
                .deletedBy(entity.deletedBy)
                .createTime(entity.createTime)
                .lastModifyTime(entity.lastModifyTime)
                .deleteTime(entity.deleteTime)
                .remark("")
                .deleted(entity.deleted)
                .version(0)
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber().toRawString())
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
                .deleted(po.getDeleted())
                .firstName(po.getFirstName())
                .lastName(po.getLastName())
                .email(po.getEmail())
                .phoneNumber(PhoneNumber.fromRawString(po.getPhoneNumber()))
                .gender(po.getGender())
                .status(po.getStatus())
                .build();
    }
}
