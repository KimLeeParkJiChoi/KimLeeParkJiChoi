package com.sparta.spangeats.domain.menu.exception;

public class MenuNotFoundException extends CustomException {

    public MenuNotFoundException() {
        super("메뉴를 찾을 수 없습니다.");
    }

    public MenuNotFoundException(String message) {
        super(message);
    }
}
