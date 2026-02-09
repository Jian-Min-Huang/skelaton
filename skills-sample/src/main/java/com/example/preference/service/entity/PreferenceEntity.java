package com.example.preference.service.entity;

import com.example.preference.service.enu.AppearanceMode;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.Instant;

@Builder(toBuilder = true)
@Value
@With
public class PreferenceEntity {
    private static final Long DEFAULT_PREFERENCE_ID = -1L;
    private static final Integer DEFAULT_PRIORITY = 0;

    public static final PreferenceEntity DEFAULT_PREFERENCE = PreferenceEntity
            .builder()
            .id(DEFAULT_PREFERENCE_ID)
            .name("Default Preference")
            .description("This is the default preference.")
            .priority(DEFAULT_PRIORITY)
            .enabled(true)
            .appearanceMode(AppearanceMode.SYSTEM)
            .deleted(false)
            .build();

    // common fields
    Long id;
    String createdBy;
    String lastModifiedBy;
    String deletedBy;
    Instant createTime;
    Instant lastModifyTime;
    Instant deleteTime;
    Boolean deleted;

    // other fields
    String name;
    String description;
    Integer priority;
    Boolean enabled;
    AppearanceMode appearanceMode;
}
