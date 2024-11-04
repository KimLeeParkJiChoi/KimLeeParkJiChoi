package com.sparta.spangeats.domain.store.exception;

public class StoreLimitExceededException extends RuntimeException {
    public StoreLimitExceededException(String message) {
        super(message);
    }
}