package com.example.skill.repository;

import com.example.skill.service.entity.SkillEntity;
import com.example.skill.service.enu.Category;

import java.util.List;
import java.util.Optional;

public interface SkillRepository {
    SkillEntity save(final SkillEntity entity);

    Optional<SkillEntity> findById(final Long id);

    List<SkillEntity> findAllByCriteria(final String name, final Category category);

    Integer update(final SkillEntity entity);

    void deleteById(final Long id);

    boolean existsById(final Long id);
}
