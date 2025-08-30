package com.example.member.domain.entity;

import com.example.common.ca.domain.Entity;
import com.example.member.domain.event.CreateMemberEvent;
import com.example.member.domain.event.ModifyMemberEvent;
import com.example.member.domain.event.RemoveMemberEvent;
import com.example.member.domain.vo.PhoneNumber;
import com.example.member.domain.vo.enu.Gender;
import com.example.member.domain.vo.enu.MemberStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@SuperBuilder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Member extends Entity<Long> {
    private String firstName;
    private String lastName;
    private String email;
    private PhoneNumber phoneNumber;
    private Gender gender;
    private MemberStatus status;

    public static CreateMemberEvent create(
            final String firstName,
            final String lastName,
            final String email,
            final PhoneNumber phoneNumber,
            final Gender gender,
            final MemberStatus status
    ) {
        final Member entity = Member
                .builder()
                .id(null)
                .createdBy(null)
                .lastModifiedBy(null)
                .deletedBy(null)
                .createTime(Instant.now())
                .lastModifyTime(Instant.now())
                .deleteTime(null)
                .deleted(0)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .gender(gender)
                .status(status)
                .build();

        return CreateMemberEvent
                .builder()
                .entity(entity)
                .build();
    }

    public ModifyMemberEvent modifyEmail(String email) {
        this.email = email;

        return ModifyMemberEvent
                .builder()
                .entity(this)
                .build();
    }

    public RemoveMemberEvent remove() {
        super.markDeleted();

        return RemoveMemberEvent
                .builder()
                .entity(this)
                .build();
    }
}