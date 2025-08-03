package com.example.member.infrastructure.data.persistence.po;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "member")
public class MemberPo {
    // common fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // other fields
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Integer gender;
    private Integer status;
}