package com.example.member.domain.entity;

import com.example.common.ddd.domain.DomainEntity;
import com.example.member.domain.event.ActivateMemberEvent;
import com.example.member.domain.event.CreatedMemberEvent;
import com.example.member.domain.event.ModifiedMemberEmailEvent;
import com.example.member.domain.event.RemovedMemberEvent;
import com.example.member.domain.vo.PhoneNumber;
import com.example.member.domain.vo.enu.Gender;
import com.example.member.domain.vo.enu.MemberStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Builder(toBuilder = true)
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
        final Gender gender
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
            .status(MemberStatus.INACTIVE)
            .build();

        return CreatedMemberEvent
            .builder()
            .entity(entity)
            .build();
    }

    public ModifiedMemberEmailEvent modifyEmail(String email) {
        if (this.email.equals(email)) {
            throw new IllegalArgumentException("same email");
        }

        final Member modifiedMember = toBuilder()
            .lastModifyTime(Instant.now())
            .email(email)
            .build();

        return ModifiedMemberEmailEvent
            .builder()
            .originalEntity(this)
            .entity(modifiedMember)
            .build();
    }

    public ActivateMemberEvent activate() {
        final Member activatedMember = toBuilder()
            .lastModifyTime(Instant.now())
            .status(MemberStatus.ACTIVE)
            .build();

        return ActivateMemberEvent
            .builder()
            .originalEntity(this)
            .entity(activatedMember)
            .build();
    }

    public RemovedMemberEvent remove() {
        final Member removedMember = toBuilder()
            .deleteTime(Instant.now())
            .deleted(1)
            .status(MemberStatus.DELETED)
            .build();

        return RemovedMemberEvent
            .builder()
            .entity(removedMember)
            .build();
    }
}
