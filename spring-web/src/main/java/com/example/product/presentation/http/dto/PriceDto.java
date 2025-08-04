package com.example.product.presentation.http.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
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
public class PriceDto {
    @Schema(example = "99.99", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Price amount must be greater than 0")
    private BigDecimal amount;
    @Schema(example = "USD", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String currency;
}