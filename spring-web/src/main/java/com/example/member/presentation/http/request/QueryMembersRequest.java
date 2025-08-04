package com.example.member.presentation.http.request;

import com.example.member.presentation.http.dto.enu.MemberStatusEnuDto;
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
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<MemberStatusEnuDto> statuses;
    @Schema(example = "0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @PositiveOrZero
    private Integer pageNumber;
    @Schema(example = "20", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Min(20)
    @Max(100)
    private Integer pageSize;
}
