package com.sparta.spangeats.domain.order.exception;

public class OrderNotOpenTimeException extends RuntimeException {
    public OrderNotOpenTimeException(String message) {
        super(message);
    }
}
