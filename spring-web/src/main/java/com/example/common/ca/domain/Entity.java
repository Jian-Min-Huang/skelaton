package com.example.common.ca.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Entity<ID> {
    private ID id;
    private String createdBy;
    private String lastModifiedBy;
    private String deletedBy;
    private Instant createTime;
    private Instant lastModifyTime;
    private Instant deleteTime;
    private String remark;
    private Integer deleted;
    private Integer version;
}
