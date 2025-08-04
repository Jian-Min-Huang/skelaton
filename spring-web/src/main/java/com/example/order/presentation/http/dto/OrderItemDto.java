package com.example.order.presentation.http.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long productId;
    @Schema(example = "iPhone 15", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String productName;
    @Schema(example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
    @Schema(example = "999.99", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0")
    private BigDecimal unitPrice;
    @Schema(example = "1999.98", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private BigDecimal totalPrice;
}