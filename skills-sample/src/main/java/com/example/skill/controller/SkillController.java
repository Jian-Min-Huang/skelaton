package com.example.skill.controller;

import com.example.skill.controller.converter.SkillConverter;
import com.example.skill.controller.protocol.SkillProtocol;
import com.example.skill.controller.request.CreateSkillRequestDto;
import com.example.skill.controller.request.ModifySkillRequestDto;
import com.example.skill.controller.request.QuerySkillsByCriteriaRequestDto;
import com.example.skill.controller.response.QuerySkillResponseDto;
import com.example.skill.service.SkillService;
import com.example.skill.service.entity.SkillEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequiredArgsConstructor
public class SkillController implements SkillProtocol {
    private final SkillService skillService;
    private final SkillConverter skillConverter;

    @Override
    public ResponseEntity<?> createSkill(final CreateSkillRequestDto request) {
        log.info("SkillController, createSkill, request: {}", request);
        final SkillEntity savedSkill = skillService.createSkill(skillConverter.toEntity(request));
        log.info("SkillController, createSkill, result: {}", savedSkill);

        return ResponseEntity.created(URI.create("/api/v1/skills/" + savedSkill.getId())).build();
    }

    @Override
    public ResponseEntity<QuerySkillResponseDto> querySkillById(final Long id) {
        log.info("SkillController, querySkillById, id: {}", id);
        final Optional<QuerySkillResponseDto> response = skillService
                .querySkillById(id)
                .map(skillConverter::toResponseDto);
        log.info("SkillController, querySkillById, result: {}", response.isPresent() ? response.get() : "not found");

        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<QuerySkillResponseDto>> querySkillsByCriteria(final QuerySkillsByCriteriaRequestDto request) {
        log.info("SkillController, querySkillsByCriteria, request: {}", request);
        final List<QuerySkillResponseDto> responses = skillService
                .querySkillsByCriteria(request.getName(), request.getCategory())
                .stream()
                .map(skillConverter::toResponseDto)
                .collect(Collectors.toList());
        log.info("SkillController, querySkillsByCriteria, result count: {}", responses.size());

        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<?> modifySkill(final Long id, final ModifySkillRequestDto request) {
        log.info("SkillController, modifySkill, id: {}, request: {}", id, request);
        final Optional<Integer> response = skillService.modifySkill(skillConverter.toEntity(id, request));
        log.info("SkillController, modifySkill, result count: {}", response.isPresent() ? response.get() : "not found");

        return response.map(v -> ResponseEntity.noContent().build()).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> removeSkill(final Long id) {
        log.info("SkillController, removeSkill, id: {}", id);
        if (!skillService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        skillService.removeSkill(id);
        log.info("SkillController, removeSkill, result: success");

        return ResponseEntity.noContent().build();
    }
}

