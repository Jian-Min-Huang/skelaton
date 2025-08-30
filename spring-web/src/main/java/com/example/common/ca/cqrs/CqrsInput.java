package com.example.common.ca.cqrs;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode
public abstract class CqrsInput<ID> {
    public ID id;
}
