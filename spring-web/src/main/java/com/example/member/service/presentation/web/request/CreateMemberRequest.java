package com.example.member.service.presentation.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberRequest {
    @Schema(example = "Vincent", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String firstName;
    @Schema(example = "Huang", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String lastName;
    @Schema(example = "vincent.huang@example.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Email
    private String email;
    @Schema(example = "+886912345678", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = "^\\+886\\d{9}$", message = "Phone number must be in format +886 followed by 9 digits, e.g., +886912345678")
    private String phoneNumber;
    @Schema(example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Min(value = 0)
    @Max(value = 2)
    private Integer gender;
}
