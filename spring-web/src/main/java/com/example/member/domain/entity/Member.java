package com.example.member.domain.entity;

import com.example.common.ca.domain.DomainEntity;
import com.example.member.domain.event.CreatedMemberEvent;
import com.example.member.domain.event.ModifiedMemberEvent;
import com.example.member.domain.event.RemovedMemberEvent;
import com.example.member.domain.vo.PhoneNumber;
import com.example.member.domain.vo.enu.Gender;
import com.example.member.domain.vo.enu.MemberStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class Member implements DomainEntity<Long> {
    // common fields
    private Long id;
    private String createdBy;
    private String lastModifiedBy;
    private String deletedBy;
    private Instant createTime;
    private Instant lastModifyTime;
    private Instant deleteTime;
    private Integer deleted;

    // other fields
    private String firstName;
    private String lastName;
    private String email;
    private PhoneNumber phoneNumber;
    private Gender gender;
    private MemberStatus status;

    public static CreatedMemberEvent create(
        final String firstName,
        final String lastName,
        final String email,
        final PhoneNumber phoneNumber,
        final Gender gender,
        final MemberStatus status) {
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

        return CreatedMemberEvent
            .builder()
            .entity(entity)
            .build();
    }

    public ModifiedMemberEvent modifyEmail(String email) {
        this.email = email;

        return ModifiedMemberEvent
            .builder()
            .entity(this)
            .build();
    }

    public RemovedMemberEvent remove() {
        this.deleteTime = Instant.now();
        this.deleted = 1;

        return RemovedMemberEvent
            .builder()
            .entity(this)
            .build();
    }
}
