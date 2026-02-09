package com.example.skill.service;

import com.example.skill.repository.SkillRepository;
import com.example.skill.service.entity.SkillEntity;
import com.example.skill.service.enu.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;

    public SkillEntity createSkill(final SkillEntity entity) {
        return skillRepository.save(entity);
    }

    public Optional<SkillEntity> querySkillById(final Long id) {
        return skillRepository.findById(id);
    }

    public List<SkillEntity> querySkillsByCriteria(final String name, final Category category) {
        return skillRepository.findAllByCriteria(name, category);
    }

    public Optional<Integer> modifySkill(final SkillEntity entity) {
        return skillRepository
                .findById(entity.getId())
                .map(existingSkill -> {
                    final SkillEntity updateEntity = existingSkill
                            .toBuilder()
                            .name(entity.getName())
                            .description(entity.getDescription())
                            .category(entity.getCategory())
                            .creator(entity.getCreator())
                            .ranking(entity.getRanking())
                            .price(entity.getPrice())
                            .voteCount(entity.getVoteCount())
                            .build();
                    return skillRepository.update(updateEntity);
                });
    }

    public void removeSkill(final Long id) {
        skillRepository.deleteById(id);
    }

    public boolean existsById(final Long id) {
        return skillRepository.existsById(id);
    }
}
