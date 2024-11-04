package com.sparta.spangeats.domain.menu.exception;

// 메뉴를 찾을 수 없을 때 발생하는 예외.
public class MenuNotFoundException extends CustomException {
    public MenuNotFoundException() {
        super("요청하신 메뉴를 찾을 수 없습니다.");
    }
}
