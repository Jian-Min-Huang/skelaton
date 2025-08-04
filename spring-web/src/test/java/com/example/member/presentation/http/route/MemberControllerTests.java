package com.example.member.presentation.http.route;

import com.example.common.TestcontainersConfiguration;
import com.example.common.data.Pagination;
import com.example.member.presentation.http.dto.PhoneNumberDto;
import com.example.member.presentation.http.dto.enu.GenderEnuDto;
import com.example.member.presentation.http.response.QueryMemberResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerTests {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreateMember() {
        final MemberTestCases memberProtocolTestCases = new MemberTestCases(webTestClient);

        final HttpHeaders headers = memberProtocolTestCases.createMember("John", "Cena", "john.cena@example.com", PhoneNumberDto.builder().countryCode("+886").number("912345678").build(), GenderEnuDto.MALE);
        final Long memberId = Long.parseLong(headers.getLocation().toString().substring(headers.getLocation().toString().lastIndexOf("/") + 1));

        QueryMemberResponse queryMemberResponse = memberProtocolTestCases.queryMemberById(memberId);
        assertThat(queryMemberResponse.getId()).isGreaterThan(0L);
        assertThat(queryMemberResponse.getFirstName()).isEqualTo("John");
        assertThat(queryMemberResponse.getLastName()).isEqualTo("Cena");
        assertThat(queryMemberResponse.getEmail()).isEqualTo("john.cena@example.com");
        assertThat(queryMemberResponse.getPhoneNumber().getCountryCode()).isEqualTo("+886");
        assertThat(queryMemberResponse.getPhoneNumber().getNumber()).isEqualTo("912345678");
        assertThat(queryMemberResponse.getGender()).isEqualTo(GenderEnuDto.MALE);

        memberProtocolTestCases.modifyMemberEmail(memberId, "john.cenar@example.com");
        queryMemberResponse = memberProtocolTestCases.queryMemberById(memberId);
        assertThat(queryMemberResponse.getId()).isEqualTo(memberId);
        assertThat(queryMemberResponse.getFirstName()).isEqualTo("John");
        assertThat(queryMemberResponse.getLastName()).isEqualTo("Cena");
        assertThat(queryMemberResponse.getEmail()).isEqualTo("john.cenar@example.com");
        assertThat(queryMemberResponse.getPhoneNumber().getCountryCode()).isEqualTo("+886");
        assertThat(queryMemberResponse.getPhoneNumber().getNumber()).isEqualTo("912345678");
        assertThat(queryMemberResponse.getGender()).isEqualTo(GenderEnuDto.MALE);

        memberProtocolTestCases.removeMemberById(queryMemberResponse.getId());
    }

    @Test
    void testQueryMembers() {
        final MemberTestCases memberProtocolTestCases = new MemberTestCases(webTestClient);

        final HttpHeaders headers1 = memberProtocolTestCases.createMember("Alice", "Johnson", "alice.johnson@example.com", PhoneNumberDto.builder().countryCode("+886").number("111111111").build(), GenderEnuDto.FEMALE);
        final HttpHeaders headers2 = memberProtocolTestCases.createMember("Bob", "Wilson", "bob.wilson@example.com", PhoneNumberDto.builder().countryCode("+886").number("222222222").build(), GenderEnuDto.MALE);
        final HttpHeaders headers3 = memberProtocolTestCases.createMember("Charlie", "Brown", "charlie.brown@example.com", PhoneNumberDto.builder().countryCode("+886").number("333333333").build(), GenderEnuDto.MALE);

        final Long memberId1 = Long.parseLong(headers1.getLocation().toString().substring(headers1.getLocation().toString().lastIndexOf("/") + 1));
        final Long memberId2 = Long.parseLong(headers2.getLocation().toString().substring(headers2.getLocation().toString().lastIndexOf("/") + 1));
        final Long memberId3 = Long.parseLong(headers3.getLocation().toString().substring(headers3.getLocation().toString().lastIndexOf("/") + 1));

        final Pagination<QueryMemberResponse> queryMemberResponses = memberProtocolTestCases.queryMembers(1, List.of(), 0, 20);

        assertThat(queryMemberResponses.getContent()).isNotEmpty();

        memberProtocolTestCases.removeMemberById(memberId1);
        memberProtocolTestCases.removeMemberById(memberId2);
        memberProtocolTestCases.removeMemberById(memberId3);
    }
}