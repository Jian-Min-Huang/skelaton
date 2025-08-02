package com.example.common.ca.cqrs;

import com.example.common.ca.interactor.UseCase;

public interface CqrsTemplate extends UseCase<CqrsInput<?>, CqrsOutput<?>> {
}
