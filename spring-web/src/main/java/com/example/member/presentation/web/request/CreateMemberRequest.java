package com.example.member.presentation.web.request;

import com.example.member.presentation.web.dto.PhoneNumberDto;
import com.example.member.presentation.web.dto.enu.GenderEnuDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    private PhoneNumberDto phoneNumber;
    private GenderEnuDto gender;
}
