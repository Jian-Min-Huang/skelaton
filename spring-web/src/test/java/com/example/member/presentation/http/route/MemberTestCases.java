package com.example.member.presentation.http.route;

import com.example.common.data.Pagination;
import com.example.member.presentation.http.dto.PhoneNumberDto;
import com.example.member.presentation.http.dto.enu.GenderEnuDto;
import com.example.member.presentation.http.dto.enu.MemberStatusEnuDto;
import com.example.member.presentation.http.request.CreateMemberRequest;
import com.example.member.presentation.http.request.ModifyMemberEmailRequest;
import com.example.member.presentation.http.request.QueryMembersRequest;
import com.example.member.presentation.http.response.QueryMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@RequiredArgsConstructor
public class MemberTestCases {
    private final WebTestClient webTestClient;

    HttpHeaders createMember(final String firstName, final String lastName, final String email, final PhoneNumberDto phoneNumber, final GenderEnuDto gender) {
        return webTestClient
                .post()
                .uri("/api/v1/members:create")
                .bodyValue(
                        CreateMemberRequest
                                .builder()
                                .firstName(firstName)
                                .lastName(lastName)
                                .email(email)
                                .phoneNumber(phoneNumber)
                                .gender(gender)
                                .build()
                )
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("Location")
                .returnResult(Void.class)
                .getResponseHeaders();
    }

    QueryMemberResponse queryMemberById(final Long id) {
        return webTestClient
                .get()
                .uri("/api/v1/members/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(QueryMemberResponse.class)
                .returnResult()
                .getResponseBody();
    }

    void queryMemberById404(final Long id) {
        webTestClient
                .get()
                .uri("/api/v1/members/{id}", id)
                .exchange()
                .expectStatus().isNotFound();
    }

    Pagination<QueryMemberResponse> queryMembers(final Integer registeredInXDays, final List<MemberStatusEnuDto> statuses, final Integer pageNumber, final Integer pageSize) {
        return webTestClient
                .post()
                .uri("/api/v1/members:queryAll")
                .bodyValue(
                        QueryMembersRequest
                                .builder()
                                .registeredInXDays(registeredInXDays)
                                .statuses(statuses)
                                .pageNumber(pageNumber)
                                .pageSize(pageSize)
                                .build()
                )
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Pagination<QueryMemberResponse>>() {
                })
                .returnResult()
                .getResponseBody();
    }

    void modifyMemberEmail(final Long id, final String email) {
        webTestClient
                .patch()
                .uri("/api/v1/members:modifyEmail")
                .bodyValue(new ModifyMemberEmailRequest(id, email))
                .exchange()
                .expectStatus().isNoContent();
    }

    void removeMemberById(final Long id) {
        webTestClient
                .delete()
                .uri("/api/v1/members/{id}", id)
                .exchange()
                .expectStatus().isNoContent();
    }
}