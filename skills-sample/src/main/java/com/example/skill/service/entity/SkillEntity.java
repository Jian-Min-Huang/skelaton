package com.example.skill.service.entity;

import com.example.skill.service.Creator;
import com.example.skill.service.enu.Category;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;
import java.time.Instant;

@Builder(toBuilder = true)
@Value
@With
public class SkillEntity {
    // common fields
    Long id;
    String createdBy;
    String lastModifiedBy;
    String deletedBy;
    Instant createTime;
    Instant lastModifyTime;
    Instant deleteTime;
    Boolean deleted;

    // custom fields
    String name;
    String description;
    Category category;
    Creator creator;
    Float ranking;
    BigDecimal price;
    Integer voteCount;
}
