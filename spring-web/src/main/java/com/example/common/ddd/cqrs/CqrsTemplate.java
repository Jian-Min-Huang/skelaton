package com.example.common.ddd.cqrs;

import com.example.common.ddd.UseCase;

public interface CqrsTemplate extends UseCase<CqrsInput<?>, CqrsOutput<?>> {
}
