package com.example.member.domain.entity;

import com.example.member.domain.event.CreateMemberEvent;
import com.example.member.domain.event.ModifyMemberEvent;
import com.example.member.domain.event.RemoveMemberEvent;
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

        CreateMemberEvent event = Member.create(firstName, lastName, email, phoneNumber, gender, status);

        assertNotNull(event);
        assertNotNull(event.getEntity());

        Member member = event.getEntity();
        assertEquals(firstName, member.getFirstName());
        assertEquals(lastName, member.getLastName());
        assertEquals(email, member.getEmail());
        assertEquals(phoneNumber, member.getPhoneNumber());
        assertEquals(gender, member.getGender());
        assertEquals(status, member.getStatus());

        assertNull(member.id);
        assertNotNull(member.createTime);
        assertNotNull(member.lastModifyTime);
        assertNull(member.deleteTime);
        assertEquals(0, member.deleted);
    }

    @Test
    void testModifyEmail() {
        CreateMemberEvent createEvent = Member.create(
                "Jane",
                "Smith",
                "jane.smith@example.com",
                PhoneNumber.builder().countryCode("+886").number("987654321").build(),
                Gender.FEMALE,
                MemberStatus.ACTIVE
        );

        Member member = createEvent.getEntity();
        String newEmail = "jane.doe@example.com";

        ModifyMemberEvent modifyEvent = member.modifyEmail(newEmail);

        assertNotNull(modifyEvent);
        assertNotNull(modifyEvent.getEntity());
        assertEquals(newEmail, member.getEmail());
        assertSame(member, modifyEvent.getEntity());
    }

    @Test
    void testRemove() {
        CreateMemberEvent createEvent = Member.create(
                "Bob",
                "Johnson",
                "bob.johnson@example.com",
                PhoneNumber.builder().countryCode("+886").number("555666777").build(),
                Gender.MALE,
                MemberStatus.ACTIVE
        );

        Member member = createEvent.getEntity();

        RemoveMemberEvent removeEvent = member.remove();

        assertNotNull(removeEvent);
        assertNotNull(removeEvent.getEntity());
        assertSame(member, removeEvent.getEntity());
        assertEquals(1, member.deleted);
        assertNotNull(member.deleteTime);
    }
}
