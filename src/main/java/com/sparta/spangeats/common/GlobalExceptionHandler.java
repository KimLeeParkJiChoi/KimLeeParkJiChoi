package com.sparta.spangeats.common;

import com.sparta.spangeats.domain.address.exception.AddressOverCountException;
import com.sparta.spangeats.domain.auth.exception.AuthException;
import com.sparta.spangeats.domain.cart.exception.CartNotFoundException;
import com.sparta.spangeats.domain.member.exception.MemberException;
import com.sparta.spangeats.domain.order.exception.OrderLowMinPriceException;
import com.sparta.spangeats.domain.order.exception.OrderNotOpenTimeException;
import com.sparta.spangeats.domain.store.exception.StoreException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
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

    @ExceptionHandler(value = MemberException.class)
    public ResponseEntity<String> handleMemberException(MemberException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<String> handleMemberException(AuthException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(OrderLowMinPriceException.class)
    public ResponseEntity<ErrorResponseDto> handleOrderLowMinPriceException(OrderLowMinPriceException ex) {
        ErrorResponseDto response = new ErrorResponseDto(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(OrderNotOpenTimeException.class)
    public ResponseEntity<ErrorResponseDto> handleOrderNotOpenTimeException(OrderNotOpenTimeException ex) {
        ErrorResponseDto response = new ErrorResponseDto(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
