package com.example.preference.repository;

import com.example.preference.service.entity.PreferenceEntity;
import com.example.preference.service.enu.AppearanceMode;

import java.util.List;
import java.util.Optional;

public interface PreferenceRepository {
    PreferenceEntity save(final PreferenceEntity entity);

    Optional<PreferenceEntity> findById(final Long id);

    Integer update(final PreferenceEntity entity);

    void deleteById(final Long id);

    boolean existsById(final Long id);
}
