package com.example.common.ddd.cqrs;

public interface UseCase<I, R> {
    R execute(I input);
}
