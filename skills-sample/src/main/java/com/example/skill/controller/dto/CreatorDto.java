package com.example.skill.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatorDto {
    @NotBlank(message = "Creator name cannot be blank")
    @Size(max = 100, message = "Creator name cannot exceed 100 characters")
    @Schema(example = "John Doe")
    private String name;

    @NotBlank(message = "Creator email cannot be blank")
    @Email(message = "Creator email must be valid")
    @Size(max = 255, message = "Creator email cannot exceed 255 characters")
    @Schema(example = "john.doe@example.com")
    private String email;
}
