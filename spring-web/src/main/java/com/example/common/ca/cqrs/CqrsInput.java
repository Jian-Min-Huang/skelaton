package com.example.common.ca.cqrs;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
public abstract class CqrsInput<ID> {
    ID id;
}
