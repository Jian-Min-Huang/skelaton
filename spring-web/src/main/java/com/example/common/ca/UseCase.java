package com.example.common.ca;

public interface UseCase<I, R> {
    R execute(I input);
}
