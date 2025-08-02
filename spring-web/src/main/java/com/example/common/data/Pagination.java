package com.example.common.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination<T> {
    private List<T> content;
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElements;

    public static <T> Pagination<T> empty() {
        return Pagination.<T>builder()
                .content(List.of())
                .currentPage(0)
                .pageSize(0)
                .totalPages(0)
                .totalElements(0L)
                .build();
    }
}