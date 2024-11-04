package com.sparta.spangeats.domain.store.exception;

public class StoreNameAlreadyExistsException extends RuntimeException {
    public StoreNameAlreadyExistsException(String message) {
        super(message);
    }
}
