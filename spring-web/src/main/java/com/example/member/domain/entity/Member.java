package com.example.member.domain.entity;

import com.example.common.ca.domain.Entity;
import com.example.member.domain.vo.PhoneNumber;
import com.example.member.domain.vo.enu.Gender;
import com.example.member.domain.vo.enu.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Member extends Entity<Long> {
    private String firstName;
    private String lastName;
    private String email;
    private PhoneNumber phoneNumber;
    private Gender gender;
    private MemberStatus status;
}