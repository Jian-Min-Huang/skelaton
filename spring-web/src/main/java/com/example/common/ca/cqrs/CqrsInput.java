package com.example.common.ca.cqrs;

import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
public abstract class CqrsInput<ID> {
    public ID id;
}
