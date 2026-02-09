package com.example.skill.controller.request;

import com.example.skill.controller.dto.CreatorDto;
import com.example.skill.service.enu.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifySkillRequestDto {
    @NotBlank(message = "Skill name cannot be blank")
    @Size(max = 100, message = "Skill name cannot exceed 100 characters")
    @Schema(example = "Java Programming")
    private String name;

    @NotBlank(message = "Skill description cannot be blank")
    @Size(max = 500, message = "Skill description cannot exceed 500 characters")
    @Schema(example = "Advanced Java programming skills")
    private String description;

    @NotNull(message = "Skill category cannot be null")
    @Schema(example = "CUSTOM")
    private Category category;

    @Valid
    @NotNull(message = "Creator cannot be null")
    private CreatorDto creator;

    @DecimalMin(value = "0.0", message = "Skill ranking must be at least 0.0")
    @DecimalMax(value = "5.0", message = "Skill ranking cannot exceed 5.0")
    @Schema(example = "4.5")
    private Float ranking;

    @DecimalMin(value = "100.0", message = "Skill price must be at least 100.0")
    @Schema(example = "120.0")
    private BigDecimal price;

    @PositiveOrZero(message = "Vote count cannot be negative")
    @Schema(example = "0")
    private Integer voteCount;
}
