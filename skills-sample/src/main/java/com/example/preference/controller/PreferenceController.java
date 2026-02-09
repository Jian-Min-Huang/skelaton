package com.example.preference.controller;

import com.example.preference.controller.converter.PreferenceConverter;
import com.example.preference.controller.protocol.PreferenceProtocol;
import com.example.preference.controller.request.CreatePreferenceRequestDto;
import com.example.preference.controller.request.ModifyPreferenceRequestDto;
import com.example.preference.controller.response.QueryPreferenceResponseDto;
import com.example.preference.service.PreferenceService;
import com.example.preference.service.entity.PreferenceEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
public class PreferenceController implements PreferenceProtocol {
    private final PreferenceService preferenceService;
    private final PreferenceConverter preferenceConverter;

    @Override
    public ResponseEntity<?> createPreference(final CreatePreferenceRequestDto request) {
        log.info("PreferenceController, createPreference, request: {}", request);
        final PreferenceEntity savedPreference = preferenceService.createPreference(preferenceConverter.toEntity(request));
        log.info("PreferenceController, createPreference, result: {}", savedPreference);

        return ResponseEntity.created(URI.create("/api/v1/preferences/" + savedPreference.getId())).build();
    }

    @Override
    public ResponseEntity<QueryPreferenceResponseDto> queryPreferenceById(final Long id) {
        log.info("PreferenceController, queryPreferenceById, id: {}", id);
        final QueryPreferenceResponseDto response = preferenceConverter.toResponseDto(preferenceService.queryPreferenceById(id));
        log.info("PreferenceController, queryPreferenceById, result: {}", response);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> modifyPreference(final Long id, final ModifyPreferenceRequestDto request) {
        log.info("PreferenceController, modifyPreference, id: {}, request: {}", id, request);
        final Optional<Integer> response = preferenceService.modifyPreference(preferenceConverter.toEntity(id, request));
        log.info("PreferenceController, modifyPreference, result count: {}", response.isPresent() ? response.get() : "not found");

        return response.map(v -> ResponseEntity.noContent().build()).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> removePreference(final Long id) {
        log.info("PreferenceController, removePreference, id: {}", id);
        if (!preferenceService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        preferenceService.removePreference(id);
        log.info("PreferenceController, removePreference, result: success");

        return ResponseEntity.noContent().build();
    }
}
