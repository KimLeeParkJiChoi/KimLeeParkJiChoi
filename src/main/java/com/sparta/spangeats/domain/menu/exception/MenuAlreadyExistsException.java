package com.sparta.spangeats.domain.menu.exception;

// 동일한 이름의 메뉴가 이미 존재할 때 발생하는 예외
public class MenuAlreadyExistsException extends CustomException {
    public MenuAlreadyExistsException(String menuName) {
        super("메뉴 이름 '" + menuName + "'은(는) 이미 존재합니다.");
    }
}
