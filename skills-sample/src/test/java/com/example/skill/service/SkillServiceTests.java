package com.example.skill.service;

import com.example.skill.repository.SkillRepository;
import com.example.skill.service.entity.SkillEntity;
import com.example.skill.service.enu.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SkillServiceTests {

    @Mock
    private SkillRepository skillRepository;

    private SkillService skillService;

    private SkillEntity skillEntity;

    @BeforeEach
    void setUp() {
        skillService = new SkillService(skillRepository);

        Creator creator = Creator
                .builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .build();

        skillEntity = SkillEntity
                .builder()
                .id(1L)
                .name("Java Programming")
                .description("Advanced Java programming skills")
                .category(Category.CUSTOM)
                .creator(creator)
                .ranking(4.5f)
                .price(new BigDecimal("120.00"))
                .voteCount(10)
                .createdBy("admin")
                .createTime(Instant.now())
                .build();
    }

    @Test
    void createSkill_ShouldReturnSavedEntity() {
        // Given
        when(skillRepository.save(any(SkillEntity.class))).thenReturn(skillEntity);

        // When
        SkillEntity result = skillService.createSkill(skillEntity);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Java Programming");
        assertThat(result.getDescription()).isEqualTo("Advanced Java programming skills");
        assertThat(result.getCategory()).isEqualTo(Category.CUSTOM);
        assertThat(result.getCreator()).isNotNull();
        assertThat(result.getCreator().getName()).isEqualTo("John Doe");
        assertThat(result.getCreator().getEmail()).isEqualTo("john.doe@example.com");
        assertThat(result.getRanking()).isEqualTo(4.5f);
        assertThat(result.getPrice()).isEqualByComparingTo(new BigDecimal("120.00"));
        assertThat(result.getVoteCount()).isEqualTo(10);
        assertThat(result.getCreatedBy()).isEqualTo("admin");
        assertThat(result.getCreateTime()).isNotNull();
        verify(skillRepository, times(1)).save(any(SkillEntity.class));
    }

    @Test
    void querySkillById_WhenSkillExists_ShouldReturnSkill() {
        // Given
        when(skillRepository.findById(1L)).thenReturn(Optional.of(skillEntity));

        // When
        Optional<SkillEntity> result = skillService.querySkillById(1L);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getName()).isEqualTo("Java Programming");
        assertThat(result.get().getDescription()).isEqualTo("Advanced Java programming skills");
        assertThat(result.get().getCategory()).isEqualTo(Category.CUSTOM);
        assertThat(result.get().getCreator()).isNotNull();
        assertThat(result.get().getCreator().getName()).isEqualTo("John Doe");
        assertThat(result.get().getCreator().getEmail()).isEqualTo("john.doe@example.com");
        assertThat(result.get().getRanking()).isEqualTo(4.5f);
        assertThat(result.get().getPrice()).isEqualByComparingTo(new BigDecimal("120.00"));
        assertThat(result.get().getVoteCount()).isEqualTo(10);
        assertThat(result.get().getCreatedBy()).isEqualTo("admin");
        assertThat(result.get().getCreateTime()).isNotNull();
        verify(skillRepository, times(1)).findById(1L);
    }

    @Test
    void querySkillById_WhenSkillNotExists_ShouldReturnEmpty() {
        // Given
        when(skillRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<SkillEntity> result = skillService.querySkillById(999L);

        // Then
        assertThat(result).isEmpty();
        verify(skillRepository, times(1)).findById(999L);
    }

    @Test
    void querySkillsByCriteria_ShouldReturnMatchingSkills() {
        // Given
        List<SkillEntity> skills = Collections.singletonList(skillEntity);
        when(skillRepository.findAllByCriteria(eq("Java"), eq(Category.CUSTOM))).thenReturn(skills);

        // When
        List<SkillEntity> result = skillService.querySkillsByCriteria("Java", Category.CUSTOM);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getId()).isEqualTo(1L);
        assertThat(result.getFirst().getName()).isEqualTo("Java Programming");
        assertThat(result.getFirst().getDescription()).isEqualTo("Advanced Java programming skills");
        assertThat(result.getFirst().getCategory()).isEqualTo(Category.CUSTOM);
        assertThat(result.getFirst().getCreator()).isNotNull();
        assertThat(result.getFirst().getCreator().getName()).isEqualTo("John Doe");
        assertThat(result.getFirst().getCreator().getEmail()).isEqualTo("john.doe@example.com");
        assertThat(result.getFirst().getRanking()).isEqualTo(4.5f);
        assertThat(result.getFirst().getPrice()).isEqualByComparingTo(new BigDecimal("120.00"));
        assertThat(result.getFirst().getVoteCount()).isEqualTo(10);
        assertThat(result.getFirst().getCreatedBy()).isEqualTo("admin");
        assertThat(result.getFirst().getCreateTime()).isNotNull();
        verify(skillRepository, times(1)).findAllByCriteria(eq("Java"), eq(Category.CUSTOM));
    }

    @Test
    void modifySkill_WhenSkillExists_ShouldReturnUpdatedCount() {
        // Given
        when(skillRepository.findById(eq(1L))).thenReturn(Optional.of(skillEntity));
        when(skillRepository.update(any(SkillEntity.class))).thenReturn(1);

        // When
        Optional<Integer> result = skillService.modifySkill(skillEntity);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(1);
        verify(skillRepository, times(1)).findById(eq(1L));
        verify(skillRepository, times(1)).update(any(SkillEntity.class));
    }

    @Test
    void modifySkill_WhenSkillNotExists_ShouldReturnEmpty() {
        // Given
        SkillEntity nonExistentSkill = SkillEntity
                .builder()
                .id(999L)
                .name("Non-existent")
                .build();
        when(skillRepository.findById(eq(999L))).thenReturn(Optional.empty());

        // When
        Optional<Integer> result = skillService.modifySkill(nonExistentSkill);

        // Then
        assertThat(result).isEmpty();
        verify(skillRepository, times(1)).findById(eq(999L));
        verify(skillRepository, times(0)).update(any(SkillEntity.class));
    }

    @Test
    void deleteSkill_ShouldCallRepositoryRemove() {
        // Given
        doNothing().when(skillRepository).deleteById(1L);

        // When
        skillService.removeSkill(1L);

        // Then
        verify(skillRepository, times(1)).deleteById(1L);
    }

    @Test
    void existsById_WhenSkillExists_ShouldReturnTrue() {
        // Given
        when(skillRepository.existsById(1L)).thenReturn(true);

        // When
        boolean result = skillService.existsById(1L);

        // Then
        assertThat(result).isTrue();
        verify(skillRepository, times(1)).existsById(1L);
    }

    @Test
    void existsById_WhenSkillNotExists_ShouldReturnFalse() {
        // Given
        when(skillRepository.existsById(999L)).thenReturn(false);

        // When
        boolean result = skillService.existsById(999L);

        // Then
        assertThat(result).isFalse();
        verify(skillRepository, times(1)).existsById(999L);
    }
}
