package com.example.preference.service;

import com.example.preference.repository.PreferenceRepository;
import com.example.preference.service.entity.PreferenceEntity;
import com.example.preference.service.enu.AppearanceMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class PreferenceService {
    private final PreferenceRepository preferenceRepository;

    public PreferenceEntity createPreference(final PreferenceEntity entity) {
        return preferenceRepository.save(entity);
    }

    public PreferenceEntity queryPreferenceById(final Long id) {
        try {
            return preferenceRepository.findById(id).orElse(PreferenceEntity.DEFAULT_PREFERENCE);
        } catch (Exception ex) {
            log.error("PreferenceService, queryPreferenceById: {}, {}", id, ex.getMessage(), ex);

            return PreferenceEntity.DEFAULT_PREFERENCE;
        }
    }

    public Optional<Integer> modifyPreference(final PreferenceEntity entity) {
        return preferenceRepository
                .findById(entity.getId())
                .map(existingPreference -> {
                    final PreferenceEntity updateEntity = existingPreference
                            .toBuilder()
                            .name(entity.getName())
                            .description(entity.getDescription())
                            .priority(entity.getPriority())
                            .enabled(entity.getEnabled())
                            .appearanceMode(entity.getAppearanceMode())
                            .build();
                    return preferenceRepository.update(updateEntity);
                });
    }

    public void removePreference(final Long id) {
        preferenceRepository.deleteById(id);
    }

    public boolean existsById(final Long id) {
        return preferenceRepository.existsById(id);
    }
}
