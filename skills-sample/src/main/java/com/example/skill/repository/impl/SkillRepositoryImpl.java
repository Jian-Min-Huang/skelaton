package com.example.skill.repository.impl;

import com.example.skill.repository.SkillRepository;
import com.example.skill.service.entity.SkillEntity;
import com.example.skill.service.enu.Category;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class SkillRepositoryImpl implements SkillRepository {
    private final ConcurrentHashMap<Long, SkillEntity> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public SkillEntity save(SkillEntity entity) {
        Long id = entity.getId() != null ? entity.getId() : idGenerator.getAndIncrement();
        Instant now = Instant.now();

        SkillEntity savedEntity = entity
                .withId(id)
                .withCreateTime(now)
                .withLastModifyTime(now)
                .withDeleted(false);

        storage.put(savedEntity.getId(), savedEntity);
        return savedEntity;
    }

    @Override
    public Optional<SkillEntity> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<SkillEntity> findAllByCriteria(String name, Category category) {
        return storage
                .values()
                .stream()
                .filter(skill -> name == null || (skill.getName() != null && skill.getName().toLowerCase().contains(name.toLowerCase())))
                .filter(skill -> category == null || category.equals(skill.getCategory()))
                .toList();
    }

    @Override
    public Integer update(SkillEntity entity) {
        if (entity.getId() != null && storage.containsKey(entity.getId())) {
            SkillEntity updatedEntity = entity.withLastModifyTime(Instant.now());
            storage.put(updatedEntity.getId(), updatedEntity);
            return 1;
        }
        return 0;
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
}
