package com.example.common.ca.cqrs;

public class CqrsOutput<T> {
    private String message;
    private ExitCode exitCode;
    private T data = null;

    private CqrsOutput(final String message, final ExitCode exitCode) {
        this.message = message;
        this.exitCode = exitCode;
    }

    private CqrsOutput(final String message, final ExitCode exitCode, final T data) {
        this.message = message;
        this.exitCode = exitCode;
        this.data = data;
    }

    public static <T> CqrsOutput<T> success(final T data) {
        return new CqrsOutput<>(
                null,
                ExitCode.SUCCESS,
                data
        );
    }

    public static <T> CqrsOutput<T> failure(final String message) {
        return new CqrsOutput<>(
                message,
                ExitCode.FAILURE
        );
    }

    public String getMessage() {
        return message;
    }

    public ExitCode getExitCode() {
        return exitCode;
    }

    public T getData() {
        return data;
    }
}
