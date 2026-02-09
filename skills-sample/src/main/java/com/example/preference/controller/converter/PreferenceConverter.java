package com.example.preference.controller.converter;

import com.example.preference.controller.request.CreatePreferenceRequestDto;
import com.example.preference.controller.request.ModifyPreferenceRequestDto;
import com.example.preference.controller.response.QueryPreferenceResponseDto;
import com.example.preference.service.entity.PreferenceEntity;
import org.springframework.stereotype.Component;

@Component
public class PreferenceConverter {
    public PreferenceEntity toEntity(final CreatePreferenceRequestDto dto) {
        return PreferenceEntity
                .builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .enabled(dto.getEnabled())
                .appearanceMode(dto.getAppearanceMode())
                .build();
    }

    public PreferenceEntity toEntity(final Long id, final ModifyPreferenceRequestDto dto) {
        return PreferenceEntity
                .builder()
                .id(id)
                .name(dto.getName())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .enabled(dto.getEnabled())
                .appearanceMode(dto.getAppearanceMode())
                .build();
    }

    public QueryPreferenceResponseDto toResponseDto(final PreferenceEntity entity) {
        return QueryPreferenceResponseDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .priority(entity.getPriority())
                .enabled(entity.getEnabled())
                .appearanceMode(entity.getAppearanceMode())
                .createTime(entity.getCreateTime())
                .lastModifyTime(entity.getLastModifyTime())
                .build();
    }
}
