package com.example.order.presentation.http.request;

import com.example.order.presentation.http.dto.enu.OrderStatusEnuDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyOrderStatusRequest {
    @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long id;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private OrderStatusEnuDto status;
}