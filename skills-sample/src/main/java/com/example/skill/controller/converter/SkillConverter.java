package com.example.skill.controller.converter;

import com.example.skill.controller.dto.CreatorDto;
import com.example.skill.controller.request.CreateSkillRequestDto;
import com.example.skill.controller.request.ModifySkillRequestDto;
import com.example.skill.controller.response.QuerySkillResponseDto;
import com.example.skill.service.Creator;
import com.example.skill.service.entity.SkillEntity;
import org.springframework.stereotype.Component;

@Component
public class SkillConverter {
    public SkillEntity toEntity(final CreateSkillRequestDto dto) {
        return SkillEntity
                .builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .category(dto.getCategory())
                .creator(toCreator(dto.getCreator()))
                .ranking(dto.getRanking())
                .price(dto.getPrice())
                .voteCount(dto.getVoteCount())
                .build();
    }
    
    public SkillEntity toEntity(final Long id, final ModifySkillRequestDto dto) {
        return SkillEntity
                .builder()
                .id(id)
                .name(dto.getName())
                .description(dto.getDescription())
                .category(dto.getCategory())
                .creator(toCreator(dto.getCreator()))
                .ranking(dto.getRanking())
                .price(dto.getPrice())
                .voteCount(dto.getVoteCount())
                .build();
    }
    
    public QuerySkillResponseDto toResponseDto(final SkillEntity entity) {
        return QuerySkillResponseDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .category(entity.getCategory())
                .creator(toCreatorDto(entity.getCreator()))
                .ranking(entity.getRanking())
                .price(entity.getPrice())
                .voteCount(entity.getVoteCount())
                .createTime(entity.getCreateTime())
                .lastModifyTime(entity.getLastModifyTime())
                .build();
    }

    private Creator toCreator(final CreatorDto dto) {
        if (dto == null) {
            return null;
        }
        return Creator
                .builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }

    private CreatorDto toCreatorDto(final Creator creator) {
        if (creator == null) {
            return null;
        }
        return CreatorDto
                .builder()
                .name(creator.getName())
                .email(creator.getEmail())
                .build();
    }
}
