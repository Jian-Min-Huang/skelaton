package com.example.order.presentation.http.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryOrdersRequest {
    @Schema(example = "0", requiredMode = Schema.RequiredMode.NOT_REQUIRED, defaultValue = "0")
    @Min(0)
    private Integer page = 0;
    @Schema(example = "20", requiredMode = Schema.RequiredMode.NOT_REQUIRED, defaultValue = "20")
    @Min(1)
    private Integer size = 20;
    @Schema(example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long customerId;
}