package com.example.preference.controller.request;

import com.example.preference.service.enu.AppearanceMode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPreferenceRequestDto {
    @NotBlank(message = "Preference name cannot be blank")
    @Size(max = 100, message = "Preference name cannot exceed 100 characters")
    @Schema(example = "Dark Mode Preference")
    private String name;

    @NotBlank(message = "Preference description cannot be blank")
    @Size(max = 500, message = "Preference description cannot exceed 500 characters")
    @Schema(example = "User preference for dark mode settings")
    private String description;

    @Min(value = 1, message = "Priority must be at least 1")
    @Max(value = 100, message = "Priority cannot exceed 100")
    @Schema(example = "10")
    private Integer priority;

    @NotNull(message = "Enabled status cannot be null")
    @Schema(example = "true")
    private Boolean enabled;

    @NotNull(message = "Appearance mode cannot be null")
    @Schema(example = "DARK")
    private AppearanceMode appearanceMode;
}
