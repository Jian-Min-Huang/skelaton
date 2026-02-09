package com.example.preference.controller.protocol;

import com.example.preference.controller.request.CreatePreferenceRequestDto;
import com.example.preference.controller.request.ModifyPreferenceRequestDto;
import com.example.preference.controller.response.QueryPreferenceResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Preference", description = "Preference APIs")
public interface PreferenceProtocol {
    @Operation(summary = "Create a new preference")
    @PostMapping("/api/v1/preferences")
    ResponseEntity<?> createPreference(@Validated @RequestBody final CreatePreferenceRequestDto request);

    @Operation(summary = "Query preference by ID")
    @GetMapping("/api/v1/preferences/{id}")
    ResponseEntity<QueryPreferenceResponseDto> queryPreferenceById(@Parameter(description = "Preference ID", required = true) @Positive @PathVariable final Long id);

    @Operation(summary = "Modify preference")
    @PutMapping("/api/v1/preferences/{id}")
    ResponseEntity<?> modifyPreference(
            @Parameter(description = "Preference ID", required = true) @Positive @PathVariable final Long id,
            @Validated @RequestBody final ModifyPreferenceRequestDto request
    );

    @Operation(summary = "Remove preference")
    @DeleteMapping("/api/v1/preferences/{id}")
    ResponseEntity<?> removePreference(@Parameter(description = "Preference ID", required = true) @Positive @PathVariable final Long id);
}
