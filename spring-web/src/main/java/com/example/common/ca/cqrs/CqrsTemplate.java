package com.example.common.ca.cqrs;

import com.example.common.ca.UseCase;

public interface CqrsTemplate extends UseCase<CqrsInput<?>, CqrsOutput<?>> {
}
