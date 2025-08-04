package com.example.order.presentation.http.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    @Schema(example = "123 Main St", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String street;
    @Schema(example = "San Francisco", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String city;
    @Schema(example = "CA", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String state;
    @Schema(example = "94102", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String zipCode;
    @Schema(example = "USA", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String country;
}