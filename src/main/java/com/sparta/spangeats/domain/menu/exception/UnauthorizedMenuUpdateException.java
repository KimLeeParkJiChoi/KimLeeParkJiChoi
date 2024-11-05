package com.sparta.spangeats.domain.menu.exception;

// 메뉴 업데이트 권한이 없을 때 발생하는 예외.
public class UnauthorizedMenuUpdateException extends CustomException {
    public UnauthorizedMenuUpdateException() {
        super("이 메뉴를 업데이트할 권한이 없습니다.");
    }
}

