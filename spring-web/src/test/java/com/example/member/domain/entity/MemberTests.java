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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class MemberTests {
    @Test
    void testCreate() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        PhoneNumber phoneNumber = PhoneNumber.builder()
            .countryCode("+886")
            .number("123456789")
            .build();
        Gender gender = Gender.MALE;
        MemberStatus status = MemberStatus.ACTIVE;

        CreatedMemberEvent event = Member.create(firstName, lastName, email, phoneNumber, gender, status);

        assertNotNull(event);
        assertNotNull(event.extractEntity());

        Member member = event.extractEntity();
        assertEquals(firstName, member.getFirstName());
        assertEquals(lastName, member.getLastName());
        assertEquals(email, member.getEmail());
        assertEquals(phoneNumber, member.getPhoneNumber());
        assertEquals(gender, member.getGender());
        assertEquals(status, member.getStatus());

        assertNull(member.getId());
        assertNotNull(member.getCreateTime());
        assertNotNull(member.getLastModifyTime());
        assertNull(member.getDeleteTime());
        assertEquals(0, member.getDeleted());
    }

    @Test
    void testModifyEmail() {
        CreatedMemberEvent createEvent = Member.create(
            "Jane",
            "Smith",
            "jane.smith@example.com",
            PhoneNumber.builder().countryCode("+886").number("987654321").build(),
            Gender.FEMALE,
            MemberStatus.ACTIVE
        );

        Member member = createEvent.extractEntity();
        String newEmail = "jane.doe@example.com";

        ModifiedMemberEvent modifyEvent = member.modifyEmail(newEmail);

        assertNotNull(modifyEvent);
        assertNotNull(modifyEvent.extractEntity());
        assertEquals(newEmail, member.getEmail());
        assertSame(member, modifyEvent.extractEntity());
    }

    @Test
    void testRemove() {
        CreatedMemberEvent createEvent = Member.create(
            "Bob",
            "Johnson",
            "bob.johnson@example.com",
            PhoneNumber.builder().countryCode("+886").number("555666777").build(),
            Gender.MALE,
            MemberStatus.ACTIVE
        );

        Member member = createEvent.extractEntity();

        RemovedMemberEvent removeEvent = member.remove();

        assertNotNull(removeEvent);
        assertNotNull(removeEvent.extractEntity());
        assertSame(member, removeEvent.extractEntity());
        assertEquals(1, member.getDeleted());
        assertNotNull(member.getDeleteTime());
    }
}
