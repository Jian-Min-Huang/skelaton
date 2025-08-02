package com.example.common.ca.interactor;

public interface UseCase<I, R> {
    R execute(I input);
}
