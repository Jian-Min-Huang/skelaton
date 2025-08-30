package com.example.member.domain.entity;

import com.example.member.domain.event.CreatedMemberEvent;
import com.example.member.domain.event.ModifiedMemberEvent;
import com.example.member.domain.event.RemovedMemberEvent;
import com.example.member.domain.vo.PhoneNumber;
import com.example.member.domain.vo.enu.Gender;
import com.example.member.domain.vo.enu.MemberStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MemberTests {
    @Test
    void testCreate() {
        final String firstName = "John";
        final String lastName = "Doe";
        final String email = "john.doe@example.com";
        final PhoneNumber phoneNumber = PhoneNumber
            .builder()
            .countryCode("+886")
            .number("123456789")
            .build();
        final Gender gender = Gender.MALE;

        final CreatedMemberEvent createdEvent = Member.create(firstName, lastName, email, phoneNumber, gender);
        final Member createdMember = createdEvent.extractEntity();

        assertNotNull(createdEvent);
        assertNotNull(createdMember);

        assertNull(createdMember.getId());
        assertNull(createdMember.getCreatedBy());
        assertNull(createdMember.getLastModifiedBy());
        assertNull(createdMember.getDeletedBy());
        assertNotNull(createdMember.getCreateTime());
        assertNotNull(createdMember.getLastModifyTime());
        assertNull(createdMember.getDeleteTime());
        assertEquals(0, createdMember.getDeleted());

        assertEquals(firstName, createdMember.getFirstName());
        assertEquals(lastName, createdMember.getLastName());
        assertEquals(email, createdMember.getEmail());
        assertEquals(phoneNumber, createdMember.getPhoneNumber());
        assertEquals(gender, createdMember.getGender());
        assertEquals(MemberStatus.INACTIVE, createdMember.getStatus());
    }

    @Test
    void testModifyEmail() {
        final CreatedMemberEvent createdEvent = Member.create(
            "Jane",
            "Smith",
            "jane.smith@example.com",
            PhoneNumber.builder().countryCode("+886").number("987654321").build(),
            Gender.FEMALE
        );
        final Member createdMember = createdEvent.extractEntity();

        final String newEmail = "jane.doe@example.com";
        final ModifiedMemberEvent modifiedEvent = createdMember.modifyEmail(newEmail);
        final Member modifiedMember = modifiedEvent.extractEntity();

        assertNotNull(createdEvent);
        assertNotNull(createdMember);
        assertNotNull(modifiedEvent);
        assertNotNull(modifiedMember);
        assertNotSame(createdMember, modifiedMember);
        assertEquals(newEmail, modifiedMember.getEmail());
    }

    @Test
    void testActiveUser() {
        final CreatedMemberEvent createdEvent = Member.create(
            "Alice",
            "Brown",
            "alice.brown@example.com",
            PhoneNumber.builder().countryCode("+886").number("111222333").build(),
            Gender.FEMALE
        );
        final Member createdMember = createdEvent.extractEntity();

        final ModifiedMemberEvent activatedEvent = createdMember.activate();
        final Member activatedMember = activatedEvent.extractEntity();

        assertNotNull(createdEvent);
        assertNotNull(createdMember);
        assertNotNull(activatedEvent);
        assertNotNull(activatedMember);
        assertNotSame(createdMember, activatedMember);
        assertEquals(MemberStatus.ACTIVE, activatedMember.getStatus());
    }

    @Test
    void testRemove() {
        final CreatedMemberEvent createdEvent = Member
            .create(
                "Bob",
                "Johnson",
                "bob.johnson@example.com",
                PhoneNumber.builder().countryCode("+886").number("555666777").build(),
                Gender.MALE
            );
        final Member createdMember = createdEvent.extractEntity();

        final RemovedMemberEvent removedEvent = createdMember.remove();
        final Member removedMember = removedEvent.extractEntity();

        assertNotNull(createdEvent);
        assertNotNull(createdMember);
        assertNotNull(removedEvent);
        assertNotNull(removedMember);
        assertNotSame(createdMember, removedMember);
        assertNotNull(removedMember.getDeleteTime());
        assertEquals(1, removedMember.getDeleted());
        assertEquals(MemberStatus.DELETED, removedMember.getStatus());
    }
}
