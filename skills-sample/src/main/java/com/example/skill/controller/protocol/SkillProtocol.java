package com.example.skill.controller.protocol;

import com.example.skill.controller.request.CreateSkillRequestDto;
import com.example.skill.controller.request.ModifySkillRequestDto;
import com.example.skill.controller.request.QuerySkillsByCriteriaRequestDto;
import com.example.skill.controller.response.QuerySkillResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Skill", description = "Skill APIs")
public interface SkillProtocol {
    @Operation(summary = "Create a new skill")
    @PostMapping("/api/v1/skills")
    ResponseEntity<?> createSkill(@Validated @RequestBody final CreateSkillRequestDto request);

    @Operation(summary = "Query skill by ID")
    @GetMapping("/api/v1/skills/{id}")
    ResponseEntity<QuerySkillResponseDto> querySkillById(@Parameter(description = "Skill ID", required = true) @Positive @PathVariable final Long id);

    @Operation(summary = "Query skills by criteria")
    @GetMapping("/api/v1/skills")
    ResponseEntity<List<QuerySkillResponseDto>> querySkillsByCriteria(@Validated final QuerySkillsByCriteriaRequestDto request);

    @Operation(summary = "Modify skill")
    @PutMapping("/api/v1/skills/{id}")
    ResponseEntity<?> modifySkill(
            @Parameter(description = "Skill ID", required = true) @Positive @PathVariable final Long id,
            @Validated @RequestBody final ModifySkillRequestDto request
    );

    @Operation(summary = "Remove skill")
    @DeleteMapping("/api/v1/skills/{id}")
    ResponseEntity<?> removeSkill(@Parameter(description = "Skill ID", required = true) @Positive @PathVariable final Long id);
}
