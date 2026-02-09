package com.example.preference.controller.response;

import com.example.preference.service.enu.AppearanceMode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Builder
@Value
public class QueryPreferenceResponseDto {
    @Schema(description = "Preference ID", example = "1")
    Long id;

    @Schema(description = "Preference name", example = "Dark Mode Preference")
    String name;

    @Schema(description = "Preference description", example = "User preference for dark mode settings")
    String description;

    @Schema(description = "Priority", example = "10")
    Integer priority;

    @Schema(description = "Enabled status", example = "true")
    Boolean enabled;

    @Schema(description = "Appearance mode", example = "DARK")
    AppearanceMode appearanceMode;

    @Schema(description = "Create time")
    Instant createTime;

    @Schema(description = "Last modify time")
    Instant lastModifyTime;
}
