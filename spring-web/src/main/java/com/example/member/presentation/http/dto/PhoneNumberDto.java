package com.example.member.presentation.http.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberDto {
    @Schema(example = "+886", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = "^\\+886", message = "Phone number must be in format +886, e.g., +886")
    @NotBlank
    private String countryCode;
    @Schema(example = "912345678", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = "\\d{9}$", message = "Phone number must followed by 9 digits, e.g., 912345678")
    @NotBlank
    private String number;
}
