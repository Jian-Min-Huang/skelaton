package com.example.common.ca.domain;

import java.time.Instant;

public abstract class Entity<ID> {
    ID id;
    String createdBy;
    String lastModifiedBy;
    String deletedBy;
    Instant createTime;
    Instant lastModifyTime;
    Instant deleteTime;
    String remark;
    Integer deleted;
    Integer version;
}
