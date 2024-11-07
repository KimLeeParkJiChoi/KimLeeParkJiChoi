package com.sparta.spangeats.domain.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrderLowMinPriceException extends RuntimeException {
    public OrderLowMinPriceException(String message) {
        super(message);
    }
}
