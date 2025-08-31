package com.example.common.ddd.domain;

import java.io.Serializable;

public interface DomainEntity<ID> extends Serializable {
    ID getId();
}
