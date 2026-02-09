package com.example.preference.service;

import com.example.preference.repository.PreferenceRepository;
import com.example.preference.service.entity.PreferenceEntity;
import com.example.preference.service.enu.AppearanceMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PreferenceServiceTests {

    @Mock
    private PreferenceRepository preferenceRepository;

    private PreferenceService preferenceService;

    private PreferenceEntity preferenceEntity;

    @BeforeEach
    void setUp() {
        preferenceService = new PreferenceService(preferenceRepository);

        preferenceEntity = PreferenceEntity
                .builder()
                .id(1L)
                .name("Dark Mode Preference")
                .description("User preference for dark mode settings")
                .priority(10)
                .enabled(true)
                .appearanceMode(AppearanceMode.DARK)
                .createdBy("admin")
                .createTime(Instant.now())
                .build();
    }

    @Test
    void createPreference_ShouldReturnSavedEntity() {
        // Given
        when(preferenceRepository.save(any(PreferenceEntity.class))).thenReturn(preferenceEntity);

        // When
        PreferenceEntity result = preferenceService.createPreference(preferenceEntity);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Dark Mode Preference");
        assertThat(result.getDescription()).isEqualTo("User preference for dark mode settings");
        assertThat(result.getPriority()).isEqualTo(10);
        assertThat(result.getEnabled()).isTrue();
        assertThat(result.getAppearanceMode()).isEqualTo(AppearanceMode.DARK);
        assertThat(result.getCreatedBy()).isEqualTo("admin");
        assertThat(result.getCreateTime()).isNotNull();
        verify(preferenceRepository, times(1)).save(any(PreferenceEntity.class));
    }

    @Test
    void queryPreferenceById_WhenPreferenceExists_ShouldReturnPreference() {
        // Given
        when(preferenceRepository.findById(1L)).thenReturn(Optional.of(preferenceEntity));

        // When
        PreferenceEntity result = preferenceService.queryPreferenceById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Dark Mode Preference");
        assertThat(result.getDescription()).isEqualTo("User preference for dark mode settings");
        assertThat(result.getPriority()).isEqualTo(10);
        assertThat(result.getEnabled()).isTrue();
        assertThat(result.getAppearanceMode()).isEqualTo(AppearanceMode.DARK);
        assertThat(result.getCreatedBy()).isEqualTo("admin");
        assertThat(result.getCreateTime()).isNotNull();
        verify(preferenceRepository, times(1)).findById(1L);
    }

    @Test
    void queryPreferenceById_WhenPreferenceNotExists_ShouldReturnDefaultPreference() {
        // Given
        when(preferenceRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        PreferenceEntity result = preferenceService.queryPreferenceById(999L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(PreferenceEntity.DEFAULT_PREFERENCE);
        verify(preferenceRepository, times(1)).findById(999L);
    }

    @Test
    void queryPreferenceById_WhenExceptionOccurs_ShouldReturnDefaultPreference() {
        // Given
        when(preferenceRepository.findById(1L)).thenThrow(new RuntimeException("Database error"));

        // When
        PreferenceEntity result = preferenceService.queryPreferenceById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(PreferenceEntity.DEFAULT_PREFERENCE);
        verify(preferenceRepository, times(1)).findById(1L);
    }

    @Test
    void modifyPreference_WhenPreferenceExists_ShouldReturnUpdatedCount() {
        // Given
        when(preferenceRepository.findById(eq(1L))).thenReturn(Optional.of(preferenceEntity));
        when(preferenceRepository.update(any(PreferenceEntity.class))).thenReturn(1);

        // When
        Optional<Integer> result = preferenceService.modifyPreference(preferenceEntity);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(1);
        verify(preferenceRepository, times(1)).findById(eq(1L));
        verify(preferenceRepository, times(1)).update(any(PreferenceEntity.class));
    }

    @Test
    void modifyPreference_WhenPreferenceNotExists_ShouldReturnEmpty() {
        // Given
        PreferenceEntity nonExistentPreference = PreferenceEntity
                .builder()
                .id(999L)
                .name("Non-existent")
                .build();
        when(preferenceRepository.findById(eq(999L))).thenReturn(Optional.empty());

        // When
        Optional<Integer> result = preferenceService.modifyPreference(nonExistentPreference);

        // Then
        assertThat(result).isEmpty();
        verify(preferenceRepository, times(1)).findById(eq(999L));
        verify(preferenceRepository, times(0)).update(any(PreferenceEntity.class));
    }

    @Test
    void removePreference_ShouldCallRepositoryDeleteById() {
        // Given
        doNothing().when(preferenceRepository).deleteById(1L);

        // When
        preferenceService.removePreference(1L);

        // Then
        verify(preferenceRepository, times(1)).deleteById(1L);
    }

    @Test
    void existsById_WhenPreferenceExists_ShouldReturnTrue() {
        // Given
        when(preferenceRepository.existsById(1L)).thenReturn(true);

        // When
        boolean result = preferenceService.existsById(1L);

        // Then
        assertThat(result).isTrue();
        verify(preferenceRepository, times(1)).existsById(1L);
    }

    @Test
    void existsById_WhenPreferenceNotExists_ShouldReturnFalse() {
        // Given
        when(preferenceRepository.existsById(999L)).thenReturn(false);

        // When
        boolean result = preferenceService.existsById(999L);

        // Then
        assertThat(result).isFalse();
        verify(preferenceRepository, times(1)).existsById(999L);
    }
}
