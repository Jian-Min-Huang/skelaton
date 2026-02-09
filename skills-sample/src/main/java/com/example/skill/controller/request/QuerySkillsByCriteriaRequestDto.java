package com.example.skill.controller.request;

import com.example.skill.service.enu.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuerySkillsByCriteriaRequestDto {
    @NotBlank(message = "Skill name cannot be blank")
    @Size(max = 100, message = "Skill name cannot exceed 100 characters")
    @Schema(example = "Java Programming")
    private String name;

    @NotNull(message = "Skill category cannot be null")
    @Schema(example = "CUSTOM")
    private Category category;
}
