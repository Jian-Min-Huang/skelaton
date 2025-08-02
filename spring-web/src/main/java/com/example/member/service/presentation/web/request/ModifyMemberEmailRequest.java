package com.example.member.service.presentation.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyMemberEmailRequest {
    @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @Positive
    private Long id;
    @Schema(example = "jay.chen@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @Email
    private String email;
}
