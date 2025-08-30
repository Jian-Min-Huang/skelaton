package com.example.common.ca.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode
public abstract class Entity<ID> {
    public ID id;
    public String createdBy;
    public String lastModifiedBy;
    public String deletedBy;
    public Instant createTime;
    public Instant lastModifyTime;
    public Instant deleteTime;
    //    protected String remark;
    public Integer deleted;
//    protected Integer version;

    protected void markDeleted() {
        this.deleted = 1;
        this.deleteTime = Instant.now();
    }
}
