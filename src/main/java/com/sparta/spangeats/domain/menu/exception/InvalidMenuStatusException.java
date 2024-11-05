package com.sparta.spangeats.domain.menu.exception;

// 유효하지 않은 메뉴 상태일 때 발생하는 예외
public class InvalidMenuStatusException extends CustomException {
    public InvalidMenuStatusException(String status) {
        super("유효하지 않은 메뉴 상태: " + status);
    }
}
