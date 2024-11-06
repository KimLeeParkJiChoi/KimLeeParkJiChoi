package com.sparta.spangeats.common;

import com.sparta.spangeats.domain.address.exception.AddressOverCountException;
import com.sparta.spangeats.domain.cart.exception.CartNotFoundException;
import com.sparta.spangeats.domain.store.exception.StoreException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StoreException.class)
    public ResponseEntity<ErrorResponseDto> handleStoreException(StoreException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleStoreExceptionCartNotFoundException(CartNotFoundException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(AddressOverCountException.class)
    public ResponseEntity<ErrorResponseDto> handleAddressOverCountException(AddressOverCountException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
