package com.example.common.ddd;

public interface UseCase<I, R> {
    R execute(I input);
}
