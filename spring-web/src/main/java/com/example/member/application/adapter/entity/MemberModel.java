package com.example.member.application.adapter.entity;

import com.example.member.application.adapter.vo.PhoneNumberVoModel;
import com.example.member.application.adapter.vo.enu.GenderEnuModel;
import com.example.member.application.adapter.vo.enu.MemberStatusEnuModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberModel {
    private Long id;
    private String createdBy;
    private String lastModifiedBy;
    private String deletedBy;
    private Instant createTime;
    private Instant lastModifyTime;
    private Instant deleteTime;
    private String remark;
    private Integer deleted;
    private Integer version;
    private String firstName;
    private String lastName;
    private String email;
    private PhoneNumberVoModel phoneNumber;
    private GenderEnuModel gender;
    private MemberStatusEnuModel status;
}
