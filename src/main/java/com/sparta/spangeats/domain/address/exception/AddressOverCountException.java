package com.sparta.spangeats.domain.address.exception;

public class AddressOverCountException extends RuntimeException{
    public AddressOverCountException(String message) {
        super(message);
    }
}
