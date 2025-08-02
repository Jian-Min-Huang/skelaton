package com.example.member.service.presentation.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryMembersResponse {
    private List<QueryMemberResponse> elements;
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElements;
}
