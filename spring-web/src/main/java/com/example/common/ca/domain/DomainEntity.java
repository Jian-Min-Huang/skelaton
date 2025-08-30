package com.example.common.ca.domain;

import java.io.Serializable;

public interface DomainEntity<ID> extends Serializable {
    ID getId();
}
