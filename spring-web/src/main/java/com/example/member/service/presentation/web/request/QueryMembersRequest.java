package com.example.member.service.presentation.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryMembersRequest {
    @Schema(example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Positive
    private Integer registeredInXDays;
    @Schema(example = "[0, 1]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @PositiveOrZero
    private List<Integer> statusList;
    @Schema(example = "0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Min(value = 0)
    private Integer pageNum;
    @Schema(example = "20", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Min(value = 1)
    @Max(value = 100)
    private Integer pageSize;
}
