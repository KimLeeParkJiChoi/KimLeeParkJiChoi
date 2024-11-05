package com.sparta.spangeats.domain.store.exception;

import com.sparta.spangeats.domain.store.dto.ErrorResponseDto;
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

}
