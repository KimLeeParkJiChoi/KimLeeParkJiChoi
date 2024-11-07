package com.sparta.spangeats.domain.order.exception;

public class OrderLowMinPriceException extends RuntimeException {
    public OrderLowMinPriceException(String message) {
        super(message);
    }
}
