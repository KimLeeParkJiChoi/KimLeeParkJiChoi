package com.sparta.spangeats.domain.order.exception;

//@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrderNotOpenTimeException extends RuntimeException {
    public OrderNotOpenTimeException(String message) {
        super(message);
    }
}
