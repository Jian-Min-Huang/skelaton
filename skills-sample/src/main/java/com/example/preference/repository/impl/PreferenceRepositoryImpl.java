package com.example.preference.repository.impl;

import com.example.preference.repository.PreferenceRepository;
import com.example.preference.service.entity.PreferenceEntity;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PreferenceRepositoryImpl implements PreferenceRepository {
    private final ConcurrentHashMap<Long, PreferenceEntity> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public PreferenceEntity save(PreferenceEntity entity) {
        Long id = entity.getId() != null ? entity.getId() : idGenerator.getAndIncrement();
        Instant now = Instant.now();

        PreferenceEntity savedEntity = entity
                .withId(id)
                .withCreateTime(now)
                .withLastModifyTime(now)
                .withDeleted(false);

        storage.put(savedEntity.getId(), savedEntity);
        return savedEntity;
    }

    @Override
    public Optional<PreferenceEntity> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Integer update(PreferenceEntity entity) {
        if (entity.getId() != null && storage.containsKey(entity.getId())) {
            PreferenceEntity updatedEntity = entity.withLastModifyTime(Instant.now());
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
