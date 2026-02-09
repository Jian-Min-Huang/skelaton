package com.example.skill.controller.response;

import com.example.skill.controller.dto.CreatorDto;
import com.example.skill.service.enu.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Value
public class QuerySkillResponseDto {
    @Schema(description = "Skill ID", example = "1")
    Long id;
    
    @Schema(description = "Skill name", example = "Java Programming")
    String name;
    
    @Schema(description = "Skill description", example = "Advanced Java programming skills")
    String description;
    
    @Schema(description = "Skill category", example = "CUSTOM")
    Category category;

    @Schema(description = "Creator information")
    CreatorDto creator;

    @Schema(description = "Skill ranking", example = "4.5")
    Float ranking;
    
    @Schema(description = "Skill price", example = "120.0")
    BigDecimal price;

    @Schema(description = "Vote count", example = "87")
    Integer voteCount;

    @Schema(description = "Create time")
    Instant createTime;
    
    @Schema(description = "Last modify time")
    Instant lastModifyTime;
}
